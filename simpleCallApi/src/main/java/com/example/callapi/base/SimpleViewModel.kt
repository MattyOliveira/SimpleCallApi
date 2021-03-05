package com.example.callapi.base

import androidx.lifecycle.ViewModel
import com.example.callapi.ResultApi

abstract class SimpleViewModel : ViewModel() {

    private var onSuccess: ((Any?) -> Unit?) = {}
    private var onError: (() -> Unit?) = {}

    suspend fun <T> T.safeCall(
        call: suspend () -> ResultApi
    ) {
        when(val response = call.invoke()) {
            is ResultApi.Success -> { onSuccess(response.data) }
            is ResultApi.Error -> { onError.let { onError.invoke() } }
        }
    }

    fun <T> T.onSuccess(action: ((Any?) -> Unit?)) {
        onSuccess = action
    }

    fun <T> T.onError(action: (() -> Unit?)) {
        onError = action
    }
}