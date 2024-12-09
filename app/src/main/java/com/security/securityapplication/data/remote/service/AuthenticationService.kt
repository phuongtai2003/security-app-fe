package com.security.securityapplication.data.remote.service

import com.security.securityapplication.data.dto.login.LoginRequest
import com.security.securityapplication.data.dto.login.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthenticationService {
    @POST("/api/v1/auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>
}