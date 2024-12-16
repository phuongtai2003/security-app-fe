package com.security.securityapplication.data.dto.rates

import com.google.gson.annotations.SerializedName

data class ExchangeRates(
    val success: Boolean,
    val timestamp: Long,
    val base: String,
    val date: String,
    val rates: Rates,
)

data class Rates(
    @SerializedName("USD")
    val usd: Double,
    @SerializedName("VND")
    val vnd: Double,
)
