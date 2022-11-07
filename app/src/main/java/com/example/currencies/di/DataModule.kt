package com.example.currencies.di

import com.example.currencies.data.*
import com.example.currencies.data.util.ConnectivityManagerNetworkMonitor
import com.example.currencies.data.util.NetworkMonitor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindCurrencyRateRepositoryImpl(currencyRateRepositoryImpl: CurrencyRateRepositoryImpl)
            : CurrencyRateRepository

    @Binds
    fun bindBalanceRepositoryImpl(balanceRepositoryImpl: BalanceRepositoryImpl)
            : BalanceRepository

    @Binds
    fun bindConversionRepositoryImpl(conversionRepositoryImpl: ConversionRepositoryImpl)
            : ConversionRepository

    @Binds
    fun bindNetworkMonitorImpl(connectivityManagerNetworkMonitor: ConnectivityManagerNetworkMonitor)
            : NetworkMonitor

}