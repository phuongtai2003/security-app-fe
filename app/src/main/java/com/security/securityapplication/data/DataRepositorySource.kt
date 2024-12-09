package com.security.securityapplication.data

import com.security.securityapplication.data.dto.login.LoginRequest
import com.security.securityapplication.data.dto.login.LoginResponse
import com.security.securityapplication.data.dto.profile.UserModel
import kotlinx.coroutines.flow.Flow

interface DataRepositorySource {
    suspend fun login(loginRequest: LoginRequest) : Flow<Resource<LoginResponse>>
    suspend fun getProfile() : Flow<Resource<UserModel>>
    suspend fun logout() : Flow<Resource<Boolean>>
}