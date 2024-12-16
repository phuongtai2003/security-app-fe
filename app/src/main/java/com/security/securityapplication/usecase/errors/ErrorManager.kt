package com.security.securityapplication.usecase.errors

import com.security.securityapplication.data.error.ErrorData
import com.security.securityapplication.data.error.mapper.ErrorMapperSource
import javax.inject.Inject

class ErrorManager @Inject constructor(private val errorMapper: ErrorMapperSource) : ErrorUseCase {
    override fun getError(errorCode: Int): ErrorData {
        return ErrorData(errorCode, errorMapper.errorsMap.getValue(errorCode))
    }
}