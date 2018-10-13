package com.eduardodev.currencies.data.network


sealed class NetworkResource
data class Success<T>(val data: T) : NetworkResource()
data class Failure(val error: Exception) : NetworkResource()
object Loading : NetworkResource()