package com.security.securityapplication.data

sealed class Resource<T>(
    val data: T? = null,
    val errorCode: Int? = null,
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(data: T? = null, errorCode: Int) : Resource<T>(data, errorCode)
    class Loading<T> : Resource<T>()

    override fun toString(): String {
        return when (this) {
            is Success -> "Success[data=$data]"
            is Error -> "Error[data=$data, errorCode=$errorCode]"
            is Loading -> "Loading"
        }
    }
}