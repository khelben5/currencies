package com.eduardodev.currencies.presentation.extension


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