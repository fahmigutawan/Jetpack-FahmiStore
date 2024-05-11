package com.example.core.data.kategori

import com.example.core.data.kategori.local.KategoriDao
import com.example.core.data.kategori.remote.KategoriRemoteSource
import com.example.core.model.entity.CategoryEntity
import com.example.core.model.response.kategori.SingleCategoryResponse
import com.example.core.util.getResponse
import com.example.core.util.getResponseWithTransaction
import javax.inject.Inject

class KategoriRepository @Inject constructor(
    private val localSource: KategoriDao,
    private val remoteSource: KategoriRemoteSource
){
    fun getAllCategory() = getResponseWithTransaction(
        onRetrieved = {
            localSource.deleteAllCategory()
            localSource.insertAllCategory(
                it.map {
                    CategoryEntity(
                        id = it.id,
                        image = it.image,
                        name = it.name
                    )
                }
            )
        },
        localCall = { localSource.getAllCategory() },
        mapper = {
            it.map {
                SingleCategoryResponse(
                    id = it.id,
                    name = it.name,
                    image = it.image
                )
            }
        }
    ){
        remoteSource.getAllCategory()
    }
}