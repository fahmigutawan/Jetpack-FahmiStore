package com.example.core.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.core.data.banner.local.BannerDao
import com.example.core.data.kategori.local.KategoriDao
import com.example.core.data.location.local.LocationDao
import com.example.core.data.product.local.ProductDao
import com.example.core.model.entity.BannerEntity
import com.example.core.model.entity.BestSellerProductEntity
import com.example.core.model.entity.CategoryEntity
import com.example.core.model.entity.CityEntity
import com.example.core.model.entity.DistrictEntity
import com.example.core.model.entity.ProvinceEntity
import com.example.core.model.entity.TopRatedSellerProductEntity
import com.example.core.model.entity.VillageEntity
import com.example.core.util.RoomTypeConverter

@Database(
    entities = [
        BestSellerProductEntity::class,
        TopRatedSellerProductEntity::class,
        CategoryEntity::class,
        BannerEntity::class,
        ProvinceEntity::class,
        CityEntity::class,
        DistrictEntity::class,
        VillageEntity::class
    ],
    version = 1
)
@TypeConverters(RoomTypeConverter::class)
abstract class MyRoomDb : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun kategoriDao(): KategoriDao
    abstract fun bannerDao(): BannerDao
    abstract fun locationDao(): LocationDao
}