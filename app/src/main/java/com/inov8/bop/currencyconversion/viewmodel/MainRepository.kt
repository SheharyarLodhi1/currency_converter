package com.inov8.bop.currencyconversion.viewmodel

import com.inov8.bop.currencyconversion.data.model.CurrencyResponse
import com.inov8.bop.currencyconversion.utils.Resource


interface MainRepository {

    suspend fun getRates(base: String): Resource<CurrencyResponse>
}