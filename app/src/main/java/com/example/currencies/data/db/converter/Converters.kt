package com.example.currencies.data.db.converter

import androidx.room.TypeConverter
import java.math.BigDecimal

class Converters {

    @TypeConverter
    fun stringToBigDecimal(value: String): BigDecimal {
        return BigDecimal(value)
    }

    @TypeConverter
    fun bigDecimalToString(bigDecimal: BigDecimal): String {
        return bigDecimal.toPlainString()
    }
}
