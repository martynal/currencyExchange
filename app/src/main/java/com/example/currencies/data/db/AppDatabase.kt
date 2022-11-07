package com.example.currencies.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.currencies.data.db.converter.Converters
import com.example.currencies.data.db.dao.*
import com.example.currencies.data.db.model.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.util.*

@Database(
    entities = [
        CurrencyRate::class,
        CurrencySellSelectionTimestamp::class,
        CurrencyReceiveSelectionTimestamp::class,
        Balance::class,
        Conversion::class,
    ],
    version = 1,
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun currencyRateDao(): CurrencyRateDao
    abstract fun currencySellSelectionTimestampDao(): CurrencySellSelectionTimestampDao
    abstract fun currencyReceiveSelectionTimestampDao(): CurrencyReceiveSelectionTimestampDao
    abstract fun balanceDao(): BalanceDao
    abstract fun conversionDao(): ConversionDao

    companion object {
        private const val databaseName = "currencies-db"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context, coroutineScope: CoroutineScope): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context, coroutineScope).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context, coroutineScope: CoroutineScope): AppDatabase =
            Room.databaseBuilder(context, AppDatabase::class.java, databaseName)
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        coroutineScope.launch(Dispatchers.IO) {
                            val adbInstance = getInstance(context, coroutineScope)
                            adbInstance.balanceDao().insert(PREPOPULATE_BALANCE_DATA)
                            adbInstance.currencyRateDao().insert(PREPOPULATE_BASE_CURRENCY_RATE_DATA)
                            adbInstance.currencySellSelectionTimestampDao().insert(
                                PREPOPULATE_SELL_SELECTION_DATA
                            )
                        }
                    }
                })
                .build()

        private const val START_BASE_CURRENCY = "EUR"
        private val PREPOPULATE_BALANCE_DATA = Balance(
            START_BASE_CURRENCY.hashCode().toLong(),
            START_BASE_CURRENCY,
            BigDecimal.valueOf(1000)
        )
        private val PREPOPULATE_BASE_CURRENCY_RATE_DATA=CurrencyRate(START_BASE_CURRENCY.hashCode().toLong(),START_BASE_CURRENCY,BigDecimal.ONE)
        private val PREPOPULATE_SELL_SELECTION_DATA = CurrencySellSelectionTimestamp(
            START_BASE_CURRENCY.hashCode().toLong(),
            Calendar.getInstance().timeInMillis
        )
    }
}
