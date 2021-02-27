package com.example.callapi.base

import androidx.lifecycle.ViewModel
import com.example.callapi.ResultApi

open class SimpleViewModel : ViewModel() {

    suspend fun <T> T.safeCall(
        call: suspend () -> ResultApi,
        onSuccess: (Any?) -> Unit?,
        onError: (() -> Unit?)?
    ) {
        when(val response = call.invoke()) {
            is ResultApi.Success -> { onSuccess(response.data) }
            is ResultApi.Error -> { onError?.let { onError.invoke() } }
        }
    }
}