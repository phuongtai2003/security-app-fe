package com.security.securityapplication.data.error.mapper

import android.content.Context
import com.security.securityapplication.R
import com.security.securityapplication.data.error.*
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ErrorMapper @Inject constructor(@ApplicationContext private val mContext: Context) : ErrorMapperSource {
    override fun getErrorString(errorCode: Int): String {
        return mContext.getString(errorCode)
    }

    override val errorsMap: Map<Int, String>
        get() = mapOf(
            Pair(NO_INTERNET_CONNECTION, getErrorString(R.string.no_internet_connection)),
            Pair(NETWORK_ERROR, getErrorString(R.string.network_error)),
            Pair(DEFAULT_ERROR, getErrorString(R.string.default_error_message)),
            Pair(PASSWORD_ERROR, getErrorString(R.string.password_error)),
            Pair(USER_NAME_ERROR, getErrorString(R.string.username_error)),
            Pair(CHECK_YOUR_FIELDS, getErrorString(R.string.check_your_fields))
        ).withDefault {
            getErrorString(R.string.network_error)
        }
}