package com.eduardodev.currencies.presentation.extension

import kotlin.reflect.KClass
import kotlin.reflect.full.safeCast


/**
 * Be P the first appearance of an element in this list that matches the [predicate].
 * @param predicate The predicate to be matched.
 * @return A list where P has moved to the first position. If there's no element matching the
 * [predicate], then the same list is return.
 */
fun <T> List<T>.moveItemToFirstPosition(predicate: (T) -> Boolean): List<T> {
    val index = indexOfFirst(predicate)
    if (index <= 0) return this

    val mutableList = toMutableList()
    val item = this[index]

    mutableList.removeAt(index)
    mutableList.add(0, item)

    return mutableList
}

/**
 * Checks whether the items in this list are instances of class [clazz].
 * @param clazz The class to be checked.
 * @return A list with all the items that are instances of [clazz].
 */
fun <T : Any> List<*>.asTyped(clazz: KClass<out T>): List<T> = mapNotNull {
    clazz.safeCast(it)
}