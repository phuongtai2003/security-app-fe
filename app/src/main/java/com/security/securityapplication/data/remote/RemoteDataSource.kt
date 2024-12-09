package com.security.securityapplication.data.remote

import com.security.securityapplication.data.Resource
import com.security.securityapplication.data.dto.login.LoginResponse
import com.security.securityapplication.data.dto.profile.UserModel

internal interface RemoteDataSource {
    suspend fun login(email: String, password: String): Resource<LoginResponse>
    suspend fun getProfile(): Resource<UserModel>
}