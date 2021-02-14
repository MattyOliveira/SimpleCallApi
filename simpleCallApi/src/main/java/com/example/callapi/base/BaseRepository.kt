package com.example.simplecallnetwork.base

import com.example.simplecallnetwork.ResultApi
import com.example.simplecallnetwork.utils.ErrorUtils
import retrofit2.Response
import java.lang.Exception

abstract class BaseRepository {
    suspend fun <T> safeCallApi(call: suspend () -> Response<T>) = safeResultApi(call)

    suspend fun <T> safeResultApi(call: suspend () -> Response<T>) : ResultApi {
        return try {
            val response = call.invoke()

            if (response.isSuccessful) {
                return ResultApi.Success(response.body())
            } else {
                val error = ErrorUtils.parserError(response)

                error?.let {
                    return ResultApi.Error(it.message, it.statusCode)
                } ?: run {
                    return ResultApi.Error("Retorno vazio")
                }
            }
        } catch (e: Exception) {
            return ResultApi.Empty()
        }
    }
}