package com.security.securityapplication.usecase.errors

import com.security.securityapplication.data.error.ErrorData

interface ErrorUseCase {
    fun getError(errorCode: Int) : ErrorData
}