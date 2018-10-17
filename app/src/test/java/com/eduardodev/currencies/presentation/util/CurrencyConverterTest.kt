package com.eduardodev.currencies.presentation.util

import com.eduardodev.currencies.presentation.model.Rate
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.BeforeClass
import org.junit.Test
import java.util.*

private const val DELTA: Double = 0.001

class CurrencyConverterTest {

    companion object {
        private lateinit var converter: CurrencyConverter

        @BeforeClass
        @JvmStatic
        fun setup() {
            converter = CurrencyConverter().apply {
                updateRates(listOf(
                        Rate(Currency.getInstance("USD"), 0.4),
                        Rate(Currency.getInstance("GBP"), 2.43),
                        Rate(Currency.getInstance("JPY"), 2.45)
                ))
            }
        }
    }

    @Test
    fun convert_USDtoGBP() {
        val output = converter.convert(
                Currency.getInstance("USD"),
                Currency.getInstance("GBP"),
                50.0
        )
        assertEquals(303.75, output, DELTA)
    }

    @Test
    fun convert_USDtoGBP_reverse() {
        val output = converter.convert(
                Currency.getInstance("GBP"),
                Currency.getInstance("USD"),
                303.75
        )
        assertEquals(50.0, output, DELTA)
    }

    @Test
    fun convert_GBPtoJPY() {
        val output = converter.convert(
                Currency.getInstance("GBP"),
                Currency.getInstance("JPY"),
                50.0
        )
        assertEquals(50.4115226337, output, DELTA)
    }

    @Test
    fun convert_GBPtoJPY_reverse() {
        val output = converter.convert(
                Currency.getInstance("JPY"),
                Currency.getInstance("GBP"),
                50.4115226337
        )
        assertEquals(50.0, output, DELTA)
    }

    @Test
    fun convert_JPYtoJPY() {
        val output = converter.convert(
                Currency.getInstance("JPY"),
                Currency.getInstance("JPY"),
                50.0
        )
        assertEquals(50.0, output, DELTA)
    }

    @Test
    fun convert_GBPtoTRY() {
        try {
            converter.convert(
                    Currency.getInstance("GBP"),
                    Currency.getInstance("TRY"),
                    50.0
            )
            fail("expected exception")
        } catch (exception: CurrencyConverter.CurrencyNotFoundException) {
            // Success.
        }
    }

    @Test
    fun convert_GBPtoTRY_reverse() {
        try {
            converter.convert(
                    Currency.getInstance("TRY"),
                    Currency.getInstance("GBP"),
                    50.0
            )
            fail("expected exception")
        } catch (exception: CurrencyConverter.CurrencyNotFoundException) {
            // Success.
        }
    }
}