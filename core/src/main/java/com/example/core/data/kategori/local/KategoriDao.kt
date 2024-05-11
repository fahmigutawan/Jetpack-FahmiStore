package com.example.core.data.kategori.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.core.model.entity.CategoryEntity

@Dao
interface KategoriDao {
    @Query("select * from categoryentity")
    suspend fun getAllCategory(): List<CategoryEntity>

    @Query("delete from categoryentity")
    suspend fun deleteAllCategory()

    @Insert
    suspend fun insertAllCategory(items: List<CategoryEntity>)
}