package com.example.currencies.domain.commission

import com.example.currencies.data.ConversionRepository
import com.example.currencies.data.CurrencyRateRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.math.BigDecimal


class CountCommissionRuleTest {

    private val currencyRateRepository = mockk<CurrencyRateRepository>()
    private val conversionRepository = mockk<ConversionRepository>()

    private val rule = CountCommissionRule(currencyRateRepository, conversionRepository)

    @Test
    fun conversionsCountBelow6_returnsZero() {
        every { conversionRepository.getTodayConversionCount(any()) } answers { 3 }
        val transaction = Transaction("EUR", BigDecimal.valueOf(100), "DOL", BigDecimal(99))
        val commission = rule.calculateCommission(transaction)
        assertEquals(BigDecimal.ZERO, commission)
    }

    @Test
    fun conversionsCountAbove5Below16_returnsMediumCommission() {
        every { conversionRepository.getTodayConversionCount(any()) } answers { 8 }
        val transaction = Transaction("EUR", BigDecimal.valueOf(100), "DOL", BigDecimal(99))
        val commission = rule.calculateCommission(transaction)
        assertTrue(BigDecimal.valueOf(0.7).compareTo(commission) == 0)
    }

    @Test
    fun conversionsCountAbove15_returnsHighCommission() {
        every { conversionRepository.getTodayConversionCount(any()) } answers { 20 }
        every { currencyRateRepository.getEurRate() } answers { BigDecimal.ONE }
        val transaction = Transaction("EUR", BigDecimal.valueOf(100), "DOL", BigDecimal(99))
        val commission = rule.calculateCommission(transaction)
        assertTrue(BigDecimal.valueOf(1.5).compareTo(commission) == 0)

    }

    @Test
    fun conversionsCountAbove15_notEur_returnsHighCommission() {
        every { conversionRepository.getTodayConversionCount(any()) } answers { 20 }
        every { currencyRateRepository.getEurRate() } answers { BigDecimal.valueOf(4.75) }
        val transaction = Transaction("PLN", BigDecimal.valueOf(100), "DOL", BigDecimal(99))
        val commission = rule.calculateCommission(transaction)
        assertTrue(BigDecimal.valueOf(2.625).compareTo(commission) == 0)

    }
}