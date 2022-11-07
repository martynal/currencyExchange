package com.example.currencies.domain

import com.example.currencies.data.ConversionRepository
import java.util.*
import javax.inject.Inject

class GetTodaysConversionCountUseCase @Inject constructor(
    private val conversionRepository: ConversionRepository
) {
    operator fun invoke(): Int {
        val midnight = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis
        return conversionRepository.getTodayConversionCount(midnight)
    }
}
