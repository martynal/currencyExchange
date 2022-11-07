package com.example.currencies.di

import android.content.Context
import com.example.currencies.BuildConfig
import com.example.currencies.data.api.CurrenciesApiService
import com.example.currencies.data.db.AppDatabase
import com.example.currencies.domain.commission.CommissionRulesManager
import com.example.currencies.domain.commission.CountCommissionRule
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun provideGlowApiService(retrofit: Retrofit): CurrenciesApiService =
        retrofit.create(CurrenciesApiService::class.java)

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://api.apilayer.com/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

    @Singleton
    @Provides
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
    ) = OkHttpClient.Builder()
        .addLoggingInterceptor(httpLoggingInterceptor)
        .addHeaderInterceptor()
        .build()

    private fun OkHttpClient.Builder.addLoggingInterceptor(
        httpLoggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient.Builder {
        if (BuildConfig.DEBUG) {
            this.addInterceptor(httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY))
        }
        return this
    }

    private fun OkHttpClient.Builder.addHeaderInterceptor() =
        addInterceptor { chain ->
            val request = chain
                .request()
                .newBuilder()
                .addHeader("apiKey", "RzddFJGn55YtRiJNOpL3U0gNXSWrnpSY")
                .build()

            chain.proceed(request)
        }

    @Singleton
    @Provides
    fun provideLoggingInterceptor() = HttpLoggingInterceptor()

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context, CoroutineScope(Dispatchers.Default))
    }

    @Singleton
    @Provides
    fun provideCommissionRuleManager(countTransactionRule: CountCommissionRule): CommissionRulesManager {
        return CommissionRulesManager(listOf(countTransactionRule))
    }

}