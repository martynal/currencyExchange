package com.example.currencies.data.db

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.currencies.data.db.dao.ConversionDao
import com.example.currencies.data.db.model.Conversion
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.math.BigDecimal
import java.util.*

@RunWith(AndroidJUnit4::class)
class ConversionDaoTest {
    private lateinit var conversionDao: ConversionDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).build()
        conversionDao = db.conversionDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun getTodayConversionCount() {
        val conversions = listOf(
            Conversion(
                "EUR",
                BigDecimal.valueOf(100),
                "DOL",
                BigDecimal.valueOf(99),
                BigDecimal.valueOf(0.7),
                Calendar.getInstance().timeInMillis
            ),
            Conversion(
                "EUR",
                BigDecimal.valueOf(10),
                "DOL",
                BigDecimal.valueOf(9),
                BigDecimal.valueOf(9),
                Calendar.getInstance().timeInMillis
            ),
            Conversion(
                "EUR",
                BigDecimal.valueOf(100),
                "PLN",
                BigDecimal.valueOf(99),
                BigDecimal.valueOf(0.7),
                Calendar.getInstance().apply { add(Calendar.DAY_OF_MONTH, -5) }.timeInMillis
            ),
            Conversion(
                "PLN",
                BigDecimal.valueOf(100),
                "DOL",
                BigDecimal.valueOf(99),
                BigDecimal.valueOf(0.7),
                Calendar.getInstance().timeInMillis
            )
        )
        conversionDao.insertAll(conversions)

        val midnight = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis

        val todaysConversions = conversionDao.getTodayConversionCount(midnight)
        assertEquals(3, todaysConversions)

    }
}