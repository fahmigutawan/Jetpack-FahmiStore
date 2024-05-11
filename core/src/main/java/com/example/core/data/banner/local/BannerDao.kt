package com.example.core.data.banner.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.core.model.entity.BannerEntity

@Dao
interface BannerDao {
    @Insert
    suspend fun insertAllBanner(items: List<BannerEntity>)

    @Query("select * from bannerentity")
    suspend fun getAllBanner(): List<BannerEntity>

    @Query("delete from bannerentity")
    suspend fun deleteAllBanner()
}