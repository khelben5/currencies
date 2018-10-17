package com.eduardodev.currencies.presentation.extension

import org.junit.Assert.assertEquals
import org.junit.Test

class ListExtensionsKtTest {

    @Test
    fun moveItemToFirstPosition_justOneElement() {
        val list = mutableListOf(4, 5, 62, 3, 5, 12)
        val expected = listOf(62, 4, 5, 3, 5, 12)
        list.moveItemToFirstPosition { it > 60 }
        assertEquals(expected, list)
    }

    @Test
    fun moveItemToFirstPosition_moreThanOneElement() {
        val list = mutableListOf(4, 5, 62, 3, 5, 12)
        val expected = listOf(62, 4, 5, 3, 5, 12)
        list.moveItemToFirstPosition { it > 10 }
        assertEquals(expected, list)
    }

    @Test
    fun moveItemToFirstPosition_noElements() {
        val list = mutableListOf(4, 5, 62, 3, 5, 12)
        val expected = listOf(4, 5, 62, 3, 5, 12)
        list.moveItemToFirstPosition { it > 70 }
        assertEquals(expected, list)
    }

    @Test
    fun moveItemToFirstPosition_emptyInput() {
        val list = emptyList<Int>().toMutableList()
        val expected = emptyList<Int>()
        list.moveItemToFirstPosition { it > 70 }
        assertEquals(expected, list)
    }

    @Test
    fun moveItemToFirstPosition_allElements() {
        val list = mutableListOf(4, 5, 62, 3, 5, 12)
        val expected = listOf(4, 5, 62, 3, 5, 12)
        list.moveItemToFirstPosition { it > 0 }
        assertEquals(expected, list)
    }

    @Test
    fun asTypedList_sameClasses() {
        val list: List<Any> = listOf(4, 5, 62, 3, 5, 12)
        val expected: List<Int> = listOf(4, 5, 62, 3, 5, 12)
        val output = list.asTyped(Int::class)
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