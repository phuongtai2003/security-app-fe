package com.security.securityapplication.data.remote.service

import com.security.securityapplication.data.dto.rates.ExchangeRates
import retrofit2.Response
import retrofit2.http.GET

interface ExchangeRatesService {
    @GET("/api/v1/exchange-rates/")
    suspend fun getExchangeRates(): Response<ExchangeRates>
}