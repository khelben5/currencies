package com.eduardodev.currencies.presentation.extension

import kotlin.reflect.KClass
import kotlin.reflect.full.safeCast


/**
 * Be P the first appearance of an element in this list that matches the [predicate]. This extension moves this element
 * to the first position. If there is no element matching the [predicate], then no changes are made.
 * @param predicate The predicate to be matched.
 */
fun <T> MutableList<T>.moveItemToFirstPosition(predicate: (T) -> Boolean) {
    val index = indexOfFirst(predicate)
    if (index <= 0) return

    val item = this[index]

    removeAt(index)
    add(0, item)
}

/**
 * Checks whether the items in this list are instances of class [clazz].
 * @param clazz The class to be checked.
 * @return A list with all the items that are instances of [clazz].
 */
fun <T : Any> List<*>.asTyped(clazz: KClass<out T>): List<T> = mapNotNull {
    clazz.safeCast(it)
}