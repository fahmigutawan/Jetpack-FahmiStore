package com.example.core.data.kategori.remote

import javax.inject.Inject

class KategoriRemoteSource @Inject constructor(
    private val service: KategoriService
) {
    suspend fun getAllCategory() = service.getAllCategory()
}