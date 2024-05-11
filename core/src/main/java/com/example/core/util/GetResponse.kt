package com.example.core.util

import android.util.Log
import kotlinx.coroutines.flow.flow
import retrofit2.Response

fun <T> getResponse(
    block: suspend () -> Response<T>
) = flow<Resource<T>> {
    emit(Resource.Loading())

    try {
        val res = block()
        val parsed = res.body()

        Log.d("RES", res.raw().toString())

        if (res.isSuccessful) {
            if (parsed != null) {
                emit(Resource.Success(parsed))
            } else {
                emit(Resource.Error("Error dalam mendapatkan data"))
            }
        } else {
            emit(Resource.Error("Gagal dalam mendapatkan data dari server"))
        }
    } catch (e: Exception) {
        emit(Resource.Error(e.message))
    }
}