package com.inov8.bop.currencyconversion.data.model

data class CurrencyResponse(
    /*val base: String,
    val date: String,
    val rates: Rates*/
    val success: Boolean,
    val terms: String,
    val privacy: String,
    val timestamp: String,
    val source: String,
    val quotes: Quotes
)