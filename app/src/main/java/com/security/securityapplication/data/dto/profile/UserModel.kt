package com.security.securityapplication.data.dto.profile

import com.google.gson.annotations.SerializedName

data class UserModel(
    val id: String?,
    val email: String?,
    val name: String?,
    @SerializedName("birthDate")
    val dateOfBirth: String?,
)