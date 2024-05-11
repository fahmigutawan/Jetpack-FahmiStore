package com.example.core.data.product

import android.util.Log
import com.example.core.data.product.local.ProductDao
import com.example.core.data.product.remote.ProductRemoteSource
import com.example.core.model.entity.BestSellerProductEntity
import com.example.core.model.entity.TopRatedSellerProductEntity
import com.example.core.model.response.product.SingleProductResponse
import com.example.core.util.Resource
import com.example.core.util.getResponseWithTransaction
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val localSource: ProductDao,
    private val remoteSource: ProductRemoteSource
) {
    fun getAllBestSellerProducts(): Flow<Resource<List<SingleProductResponse>>> {
        /**
         * Karena API yang terbatas, jadi sistem rekomendasi masih berdasarkan angka random
         */
        val randomNumber = (1..4).random()
        val limit = 10

        return getResponseWithTransaction(
            onRetrieved = {
                localSource.deleteAllBestSellerProduct()
                localSource.insertAllBestSellerProduct(
                    it.map { res ->
                        BestSellerProductEntity(
                            id = res.id,
                            title = res.title,
                            price = res.price,
                            description = res.description,
                            category = res.category,
                            images = res.images
                        )
                    }
                )
            },
            localCall = { localSource.getAllBestSellerProduct() },
            mapper = {
                it.map { entity ->
                    SingleProductResponse(
                        id = entity.id,
                        title = entity.title,
                        price = entity.price,
                        description = entity.description,
                        category = entity.category,
                        images = entity.images
                    )
                }
            }
        ) {
            remoteSource.getProductsByOffsetAndLimit(
                offset = randomNumber * limit - limit,
                limit = limit
            )
        }
    }

    fun getAllTopRatedProducts(): Flow<Resource<List<SingleProductResponse>>> {
        /**
         * Karena API yang terbatas, jadi sistem rekomendasi masih berdasarkan angka random
         */
        val randomNumber = (1..4).random()
        val limit = 10

        return getResponseWithTransaction(
            onRetrieved = {
                localSource.deleteAllTopRatedProduct()
                localSource.insertAllTopRatedProduct(
                    it.map { res ->
                        TopRatedSellerProductEntity(
                            id = res.id,
                            title = res.title,
                            price = res.price,
                            description = res.description,
                            category = res.category,
                            images = res.images
                        )
                    }
                )
            },
            localCall = { localSource.getAllTopRatedProductEntity() },
            mapper = {
                it.map { entity ->
                    SingleProductResponse(
                        id = entity.id,
                        title = entity.title,
                        price = entity.price,
                        description = entity.description,
                        category = entity.category,
                        images = entity.images
                    )
                }
            }
        ) {
            remoteSource.getProductsByOffsetAndLimit(
                offset = randomNumber * limit - limit,
                limit = limit
            )
        }
    }
}