package com.example.currencies.domain

import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject

class GetReceiveValueUseCase @Inject constructor() {
    operator fun invoke(sellValue: String, rate: BigDecimal): String {
        return BigDecimal(sellValue).multiply(rate)
            .setScale(2, RoundingMode.HALF_EVEN)
            .toPlainString()
    }
}
