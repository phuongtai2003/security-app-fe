package com.security.securityapplication.data.remote

import android.util.Log
import com.security.securityapplication.data.Resource
import com.security.securityapplication.data.dto.login.LoginRequest
import com.security.securityapplication.data.dto.login.LoginResponse
import com.security.securityapplication.data.dto.profile.UserModel
import com.security.securityapplication.data.dto.rates.ExchangeRates
import com.security.securityapplication.data.error.NETWORK_ERROR
import com.security.securityapplication.data.error.NO_INTERNET_CONNECTION
import com.security.securityapplication.data.remote.service.AuthenticationService
import com.security.securityapplication.data.remote.service.ExchangeRatesService
import com.security.securityapplication.data.remote.service.ProfileService
import com.security.securityapplication.utils.NetworkConnectivity
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class RemoteData @Inject constructor(
    private  val serviceGenerator: ServiceGenerator,
    private val networkConnectivity: NetworkConnectivity
) : RemoteDataSource {
    override suspend fun login(email: String, password: String): Resource<LoginResponse> {
        val authenticationService = serviceGenerator.createService(AuthenticationService::class.java)
        return when (val response = processCall { authenticationService.login(LoginRequest(email, password)) }) {
            is LoginResponse -> Resource.Success(response)
            is Int -> Resource.Error(errorCode = response)
            else -> Resource.Error(errorCode = NETWORK_ERROR)
        }
    }

    override suspend fun getProfile(): Resource<UserModel> {
        val profileService = serviceGenerator.createAuthService(ProfileService::class.java)
        return when (val response = processCall { profileService.getProfile() }) {
            is UserModel -> Resource.Success(response)
            is Int -> Resource.Error(errorCode = response)
            else -> Resource.Error(errorCode = NETWORK_ERROR)
        }
    }

    override suspend fun getExchangeRates(): Resource<ExchangeRates> {
        val exchangeRateService = serviceGenerator.createAuthService(ExchangeRatesService::class.java)
        return when (val response = processCall { exchangeRateService.getExchangeRates() }) {
            is ExchangeRates -> Resource.Success(response)
            is Int -> Resource.Error(errorCode = response)
            else -> Resource.Error(errorCode = NETWORK_ERROR)
        }
    }

    private suspend fun processCall(responseCall: suspend () -> Response<*>): Any? {
        if (!networkConnectivity.isNetworkConnected()) {
            return NO_INTERNET_CONNECTION
        }
        return try {
            val response = responseCall.invoke()
            val responseCode = response.code()
            if (response.isSuccessful) {
                response.body()
            } else {
                responseCode
            }
        } catch (e: IOException) {
            NETWORK_ERROR
        }
    }

}