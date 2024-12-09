package com.security.securityapplication.data.error

import java.lang.Exception

class ErrorData(val errorCode: Int, val errorMessage: String) {
    constructor(exception: Exception) : this(
        errorCode = DEFAULT_ERROR,
        errorMessage = exception.message ?: ""
    )
}

const val NO_INTERNET_CONNECTION = -1
const val NETWORK_ERROR = -2
const val DEFAULT_ERROR = -3
const val PASSWORD_ERROR = -4
const val USER_NAME_ERROR = -5
const val CHECK_YOUR_FIELDS = -6
