package com.example.currencies.domain

import junit.framework.Assert.assertEquals
import org.junit.Test
import java.math.BigDecimal

class GetReceiveValueUseCaseTest {

    val useCase = GetReceiveValueUseCase()

    @Test
    fun getReceiveValue_returnPlainString() {
        val receiveValue = useCase("100", BigDecimal.valueOf(0.9987))
        assertEquals("99.87", receiveValue)
    }
}