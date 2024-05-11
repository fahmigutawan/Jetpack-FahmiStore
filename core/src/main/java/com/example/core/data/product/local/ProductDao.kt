package com.example.core.data.product.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.core.model.entity.BestSellerProductEntity
import com.example.core.model.entity.TopRatedSellerProductEntity

@Dao
interface ProductDao {
    @Insert
    suspend fun insertAllBestSellerProduct(items: List<BestSellerProductEntity>)

    @Insert
    suspend fun insertAllTopRatedProduct(items: List<TopRatedSellerProductEntity>)

    @Query("select * from bestsellerproductentity")
    suspend fun getAllBestSellerProduct(): List<BestSellerProductEntity>

    @Query("select * from topratedsellerproductentity")
    suspend fun getAllTopRatedProductEntity(): List<TopRatedSellerProductEntity>

    @Query("delete from bestsellerproductentity")
    suspend fun deleteAllBestSellerProduct()

    @Query("delete from topratedsellerproductentity")
    suspend fun deleteAllTopRatedProduct()
}