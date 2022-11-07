package com.example.currencies.di

import com.example.currencies.data.db.AppDatabase
import com.example.currencies.data.db.dao.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaosModule{

    @Provides
    fun providesBalanceDao(
        database: AppDatabase,
    ): BalanceDao = database.balanceDao()

    @Provides
    fun providesCurrencyRateDao(
        database: AppDatabase,
    ): CurrencyRateDao = database.currencyRateDao()


    @Provides
    fun providesCurrencySellSelectionTimestampDao(
        database: AppDatabase,
    ): CurrencySellSelectionTimestampDao = database.currencySellSelectionTimestampDao()

    @Provides
    fun providesCurrencyReceiveSelectionTimestampDao(
        database: AppDatabase,
    ): CurrencyReceiveSelectionTimestampDao = database.currencyReceiveSelectionTimestampDao()

    @Provides
    fun providesConversionDao(
        database: AppDatabase,
    ): ConversionDao = database.conversionDao()
}