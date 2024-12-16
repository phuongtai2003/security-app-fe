package com.security.securityapplication.data.dto.login

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("accessToken")
    val token: String,
    @SerializedName("refreshToken")
    val refreshToken: String
)
