package com.example.currencies.data.api

import retrofit2.http.GET
import retrofit2.http.Query

interface CurrenciesApiService {
    @GET("exchangerates_data/latest")
    suspend fun getLatestExchangeRates(
        @Query("base") baseCurrency: String
    ): ExchangeRates
}