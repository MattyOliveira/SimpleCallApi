package com.example.callapi.base

import com.example.callapi.call.ResultApi
import com.example.callapi.utils.ErrorUtils
import retrofit2.Response
import java.lang.Exception

abstract class SimpleRepository {

    suspend fun <T> safeCallApi(call: suspend () -> Response<T>) = safeResultApi(call)

    private suspend fun <T> safeResultApi(call: suspend () -> Response<T>) : ResultApi {
        return try {
            val response = call.invoke()

            if (response.isSuccessful) {
                 ResultApi.Success(response.body())
            } else {
                val error = ErrorUtils.parserError(response)

                error?.let {
                     ResultApi.Error(it.message, it.statusCode)
                } ?: run {
                     ResultApi.Empty("Retorno vazio")
                }
            }
        } catch (e: Exception) {
             ResultApi.Error()
        }
    }
}