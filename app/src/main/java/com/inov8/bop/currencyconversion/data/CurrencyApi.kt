package com.inov8.bop.currencyconversion.data

import com.inov8.bop.currencyconversion.data.model.CurrencyResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApi {

    @GET("/live?access_key=35460dd94c56cec76f976a9802d46d15")
    suspend fun getRates(
        @Query("base") base: String
    ): Response<CurrencyResponse>
}