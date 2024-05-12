package com.example.core.data.location.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.core.model.entity.CityEntity
import com.example.core.model.entity.DistrictEntity
import com.example.core.model.entity.ProvinceEntity
import com.example.core.model.entity.VillageEntity

@Dao
interface LocationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllProvince(items: List<ProvinceEntity>)

    @Query("select * from provinceentity")
    suspend fun getAllProvince(): List<ProvinceEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCity(items: List<CityEntity>)

    @Query("select * from cityentity where province_id=:provinceId")
    suspend fun getAllCityByProvinceId(provinceId: String): List<CityEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllDistrict(items: List<DistrictEntity>)

    @Query("select * from districtentity where regency_id=:cityId")
    suspend fun getAllDistrictByCityId(cityId: String): List<DistrictEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllVillage(items: List<VillageEntity>)

    @Query("select * from villageentity where district_id=:districtId")
    suspend fun getAllVillageByDistrictId(districtId: String): List<VillageEntity>
}