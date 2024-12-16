package com.security.securityapplication.data.remote.service

import com.security.securityapplication.data.dto.profile.UserModel
import retrofit2.Response
import retrofit2.http.GET

interface ProfileService {
    @GET("/api/v1/profile/me")
    suspend fun getProfile(): Response<UserModel>
}