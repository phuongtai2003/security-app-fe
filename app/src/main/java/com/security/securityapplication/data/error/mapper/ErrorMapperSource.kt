package com.security.securityapplication.data.error.mapper

interface ErrorMapperSource {
    fun getErrorString(errorCode: Int): String
    val errorsMap : Map<Int, String>
}