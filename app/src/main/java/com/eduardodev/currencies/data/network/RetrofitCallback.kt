package com.eduardodev.currencies.data.network

import com.eduardodev.currencies.data.extension.readContent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RetrofitCallback<T>(
        private val onSuccess: (T) -> Unit,
        private val onFailure: (Exception) -> Unit
) : Callback<T> {

    override fun onFailure(call: Call<T>, error: Throwable) {
        onFailure(Exception(error))
    }

    override fun onResponse(call: Call<T>, response: Response<T>) {
        if (!response.isSuccessful) {
            onFailure(Exception(response.errorBody()?.byteStream()?.readContent()))
            return
        }

        val responseBody = response.body()
        if (responseBody == null) {
            onFailure(Exception("no body found"))
            return
        }

        onSuccess(responseBody)
    }
}