package com.eduardodev.currencies.presentation.repository


sealed class DataResource
data class Success<T>(val data: T) : DataResource()
data class Failure(val error: Exception) : DataResource()
object Loading : DataResource()