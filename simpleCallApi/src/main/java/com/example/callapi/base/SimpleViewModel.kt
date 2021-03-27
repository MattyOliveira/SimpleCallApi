package com.example.callapi.base

import androidx.lifecycle.ViewModel
import com.example.callapi.ResultApi

abstract class SimpleViewModel<T1> : ViewModel() {

    private var onSuccess: ((T1) -> Unit?) = {}
    private var onError: (() -> Unit?) = {}

    @Suppress("UNCHECKED_CAST")
    suspend fun <T> T.safeCall(
        call: suspend () -> ResultApi
    ) {
        when(val response = call.invoke()) {
            is ResultApi.Success -> { onSuccess(response.data as T1) }
            is ResultApi.Error -> { onError.let { onError.invoke() } }
        }
    }

    fun <T> T.onSuccess(action: ((T1) -> Unit?)) {
        onSuccess = action
    }

    fun <T> T.onError(action: (() -> Unit?)) {
        onError = action
    }
}