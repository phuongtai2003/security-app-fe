package com.security.securityapplication.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import javax.inject.Inject

class Network @Inject constructor(val mContext: Context) : NetworkConnectivity {
    override fun getNetworkInfo(): NetworkInfo? {
        val connectivityManager = mContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo
    }

    override fun isNetworkConnected(): Boolean {
        val networkInfo = getNetworkInfo()
        return networkInfo != null && networkInfo.isConnected
    }

}

interface NetworkConnectivity {
    fun getNetworkInfo() : NetworkInfo?
    fun isNetworkConnected() : Boolean
}