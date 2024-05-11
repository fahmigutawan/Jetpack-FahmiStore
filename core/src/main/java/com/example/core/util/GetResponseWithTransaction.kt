package com.example.core.util

import android.util.Log
import kotlinx.coroutines.flow.flow
import retrofit2.Response

fun <T, L> getResponseWithTransaction(
    onRetrieved: suspend (T) -> Unit,
    localCall: suspend () -> List<L>,
    mapper: (List<L>) -> T,
    apiCall: suspend () -> Response<T>
) = flow<Resource<T>> {
    emit(Resource.Loading())

    try {
        val res = apiCall()
        val parsed = res.body()
        if (res.isSuccessful) {
            if (parsed != null) {
                emit(Resource.Success(parsed))
                onRetrieved(parsed)
            } else {
                /** Jika gagal mendapatkan data dari API, coba dulu get data dari local */
                try {
                    val local = localCall()

                    /** Cek data dari local apakah kosong. Jika kosong, dianggap sebagai error */
                    if (local.isNotEmpty()) {
                        emit(Resource.Success(mapper(local)))
                    } else {
                        emit(Resource.Error("Gagal dalam mendapatkan data dari server"))
                    }
                } catch (e: Exception) {
                    emit(Resource.Error("Error dalam mendapatkan data"))
                }
            }
        } else {
            emit(Resource.Error("Gagal dalam mendapatkan data dari server"))
        }
    } catch (e: Exception) {
        Log.e("API CALL", e.message.toString())

        /** Jika gagal mendapatkan data dari API, coba dulu get data dari local */
        try {
            val local = localCall()

            /** Cek data dari local apakah kosong. Jika kosong, dianggap sebagai error */
            if (local.isNotEmpty()) {
                emit(Resource.Success(mapper(local)))
            } else {
                emit(Resource.Error("Gagal dalam mendapatkan data dari server"))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message))
        }
    }
}