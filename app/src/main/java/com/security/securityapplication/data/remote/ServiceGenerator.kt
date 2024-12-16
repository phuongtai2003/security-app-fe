package com.security.securityapplication.data.remote

import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import com.security.securityapplication.BASE_URL
import com.security.securityapplication.data.local.LocalData
import com.squareup.picasso.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

private const val timeoutRead = 10
private const val contentType = "Content-Type"
private const val contentTypeValue = "application/json"
private const val timeoutConnect = 10
private const val authorizationHeader = "Authorization"
private const val bearer = "Bearer "

@Singleton
class ServiceGenerator @Inject constructor(
    private val localData: LocalData
){
    private val okHttpBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
    private lateinit var retrofit: Retrofit

    private var tokenInterceptor = Interceptor { chain ->
        val original = chain.request()
        val requestBuilder = original.newBuilder()
            .header(contentType, contentTypeValue)
        val token = localData.getToken()
        if (token != null) {
            requestBuilder.header(authorizationHeader, bearer + token)
        }
        val request = requestBuilder
            .method(original.method, original.body)
            .build()
        chain.proceed(request)
    }

    private var headerInterceptor = Interceptor { chain ->
        val original = chain.request()

        val request = original.newBuilder()
            .header(contentType, contentTypeValue)
            .method(original.method, original.body)
            .build()

        chain.proceed(request)
    }

    private val logger: HttpLoggingInterceptor
        get() {
            val loggingInterceptor = HttpLoggingInterceptor()
            if (BuildConfig.DEBUG) {
                loggingInterceptor.apply { level = HttpLoggingInterceptor.Level.BODY }
            }
            return loggingInterceptor
        }

    init {
        okHttpBuilder.addInterceptor(headerInterceptor)
        okHttpBuilder.addInterceptor(logger)
        okHttpBuilder.connectTimeout(timeoutConnect.toLong(), TimeUnit.SECONDS)
        okHttpBuilder.readTimeout(timeoutRead.toLong(), TimeUnit.SECONDS)
    }

    fun <S> createService(serviceClass: Class<S>): S {
        val client = okHttpBuilder.build()
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL).client(client)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
        return retrofit.create(serviceClass)
    }

    fun<S> createAuthService(serviceClass: Class<S>): S {
        okHttpBuilder.addInterceptor(tokenInterceptor)
        val client = okHttpBuilder.build()
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL).client(client)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()

        return retrofit.create(serviceClass)
    }
}