package com.eduardodev.currencies.presentation.extension

import org.junit.Assert.assertEquals
import org.junit.Test

class ListExtensionsKtTest {

    @Test
    fun moveItemToFirstPosition_justOneElement() {
        val input = listOf(4, 5, 62, 3, 5, 12)
        val expected = listOf(62, 4, 5, 3, 5, 12)
        val output = input.moveItemToFirstPosition { it > 60 }
        assertEquals(expected, output)
    }

    @Test
    fun moveItemToFirstPosition_moreThanOneElement() {
        val input = listOf(4, 5, 62, 3, 5, 12)
        val expected = listOf(62, 4, 5, 3, 5, 12)
        val output = input.moveItemToFirstPosition { it > 10 }
        assertEquals(expected, output)
    }

    @Test
    fun moveItemToFirstPosition_noElements() {
        val input = listOf(4, 5, 62, 3, 5, 12)
        val expected = listOf(4, 5, 62, 3, 5, 12)
        val output = input.moveItemToFirstPosition { it > 70 }
        assertEquals(expected, output)
    }

    @Test
    fun moveItemToFirstPosition_emptyInput() {
        val input = emptyList<Int>()
        val expected = emptyList<Int>()
        val output = input.moveItemToFirstPosition { it > 70 }
        assertEquals(expected, output)
    }

    @Test
    fun moveItemToFirstPosition_allElements() {
        val input = listOf(4, 5, 62, 3, 5, 12)
        val expected = listOf(4, 5, 62, 3, 5, 12)
        val output = input.moveItemToFirstPosition { it > 0 }
        assertEquals(expected, output)
    }

    @Test
    fun asTypedList_sameClasses() {
        val input: List<Any> = listOf(4, 5, 62, 3, 5, 12)
        val expected: List<Int> = listOf(4, 5, 62, 3, 5, 12)
        val output = input.asTyped(Int::class)
        assertEquals(expected, output)
    }

    @Test
    fun asTypedList_differentClasses() {
        val input: List<Any> = listOf(4, 5, "Dog", 3, "Cat", 12)
        val expected: List<Int> = listOf(4, 5, 3, 12)
        val output = input.asTyped(Int::class)
        assertEquals(expected, output)
    }
}