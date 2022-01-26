package com.inov8.bop.currencyconversion.di

import com.inov8.bop.currencyconversion.data.CurrencyApi
import com.inov8.bop.currencyconversion.utils.DispatcherProvider
import com.inov8.bop.currencyconversion.viewmodel.DefaultMainRepository
import com.inov8.bop.currencyconversion.viewmodel.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

//private const val BASE_URL = "https://api.exchangeratesapi.io/"
//private const val BASE_URL = "http://api.currencylayer.com/live?access_key=35460dd94c56cec76f976a9802d46d15"
private const val BASE_URL = "http://api.currencylayer.com/"

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideCurrencyApi(): CurrencyApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(CurrencyApi::class.java)

    @Singleton
    @Provides
    fun provideMainRepository(api: CurrencyApi): MainRepository = DefaultMainRepository(api)

    @Singleton
    @Provides
    fun provideDispatchers(): DispatcherProvider = object : DispatcherProvider {
        override val main: CoroutineDispatcher
            get() = Dispatchers.Main
        override val io: CoroutineDispatcher
            get() = Dispatchers.IO
        override val default: CoroutineDispatcher
            get() = Dispatchers.Default
        override val unconfined: CoroutineDispatcher
            get() = Dispatchers.Unconfined
    }
}