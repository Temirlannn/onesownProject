package com.example.core.network

import com.example.core.network.result.Resource
import retrofit2.Response

abstract class BaseDataSource {

    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Resource<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                if (response.body() != null) return Resource.success(response.body())
            } else {
                return Resource.error(response.message(), response.body(), response.code())
            }
        } catch (e: Exception) {
            return Resource.error(e.message ?: e.toString(), null, 429)
        }
        return Resource.error(null, null, 429)
    }
}