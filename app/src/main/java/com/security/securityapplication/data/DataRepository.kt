package com.security.securityapplication.data

import android.util.Log
import com.security.securityapplication.data.dto.login.LoginRequest
import com.security.securityapplication.data.dto.login.LoginResponse
import com.security.securityapplication.data.dto.profile.UserModel
import com.security.securityapplication.data.error.DEFAULT_ERROR
import com.security.securityapplication.data.local.LocalData
import com.security.securityapplication.data.remote.RemoteData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class DataRepository @Inject constructor(
    private val remoteDataSource: RemoteData,
    private val localDataSource: LocalData,
    private val ioDispatcher: CoroutineContext
) : DataRepositorySource {
    override suspend fun login(loginRequest: LoginRequest): Flow<Resource<LoginResponse>> {
        return flow {
            remoteDataSource.login(loginRequest.email, loginRequest.password).let {
                if(it.data != null) {
                    localDataSource.saveToken(it.data.token)
                    localDataSource.saveRefreshToken(it.data.refreshToken)
                }
                Log.d("DataRepository", "login: $it")
                emit(it)
            }
        }.flowOn(ioDispatcher)
    }

    override suspend fun getProfile(): Flow<Resource<UserModel>> {
        return flow {
            remoteDataSource.getProfile().let {
                Log.d("DataRepository", "getProfile: $it")
                emit(it)
            }
        }.flowOn(ioDispatcher)
    }

    override suspend fun logout(): Flow<Resource<Boolean>> {
        return flow {
            try{
                localDataSource.logout()
                emit(Resource.Success(true))
            }
            catch (e: Exception){
                emit(Resource.Error(false, DEFAULT_ERROR))
            }
        }.flowOn(ioDispatcher)
    }
}