package com.eduardodev.currencies.presentation.model

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

private const val DELTA = 0.001

class ConversionListTest {

    private val dollar = Currency.getInstance("USD")
    private val euro = Currency.getInstance("EUR")
    private val yen = Currency.getInstance("JPY")
    private val pound = Currency.getInstance("GBP")

    @Test
    fun test_initial() {
        val list = ConversionList()

        val originalRates = listOf(
                Rate(dollar, 0.45),
                Rate(euro, 5.65),
                Rate(yen, 3.89)
        )

        list.updateRates(originalRates)
        originalRates.forEach { assertEquals(it.value, list.valueForCurrency(it.currency)) }
    }

    @Test
    fun test_selectAnotherAndUpdate() {
        val list = ConversionList()

        val originalRates = listOf(
                Rate(dollar, 0.45),
                Rate(euro, 5.65),
                Rate(yen, 3.89),
                Rate(pound, 12.0)
        )

        val newRates = listOf(
                Rate(dollar, 23.2),
                Rate(euro, 43.1),
                Rate(yen, 534.2),
                Rate(pound, 12.0)
        )

        list.updateRates(originalRates)
        list.selectCurrency(yen)

        assertEquals(yen, list.getSelectedCurrency())

        list.updateRates(newRates)

        assertEquals(yen, list.getSelectedCurrency())
    }

    @Test
    fun test_addAnother() {
        val list = ConversionList()

        val originalRates = listOf(
                Rate(dollar, 0.45),
                Rate(euro, 5.65),
                Rate(yen, 3.89)
        )

        val newRates = listOf(
                Rate(dollar, 23.2),
                Rate(euro, 43.1),
                Rate(yen, 534.2),
                Rate(pound, 12.0)
        )

        list.updateRates(originalRates)
        list.updateRates(newRates)

        assertEquals(0.45, list.valueForCurrency(dollar)!!, DELTA)
        assertEquals(0.8359913793, list.valueForCurrency(euro)!!, DELTA)
        assertEquals(10.361637931, list.valueForCurrency(yen)!!, DELTA)
        assertEquals(0.2327586207, list.valueForCurrency(pound)!!, DELTA)
    }

    @Test
    fun test_changeValue() {
        val list = ConversionList()

        val originalRates = listOf(
                Rate(dollar, 0.45),
                Rate(euro, 5.65),
                Rate(yen, 3.89)
        )

        list.updateRates(originalRates)
        list.selectCurrency(euro)
        list.setValueForSelectedCurrency(50.0)

        assertEquals(50.0, list.valueForCurrency(euro)!!, DELTA)
        assertEquals(3.982300885, list.valueForCurrency(dollar)!!, DELTA)
    }

    @Test
    fun test_changeValueAndUpdate() {
        val list = ConversionList()

        val originalRates = listOf(
                Rate(dollar, 0.45),
                Rate(euro, 5.65),
                Rate(yen, 3.89)
        )

        val newRates = listOf(
                Rate(dollar, 23.2),
                Rate(euro, 43.1),
                Rate(yen, 534.2),
                Rate(pound, 12.0)
        )

        list.updateRates(originalRates)
        list.selectCurrency(euro)
        list.setValueForSelectedCurrency(50.0)
        list.updateRates(newRates)

        assertEquals(50.0, list.valueForCurrency(euro)!!, DELTA)
        assertEquals(26.9141531323, list.valueForCurrency(dollar)!!, DELTA)
    }
}