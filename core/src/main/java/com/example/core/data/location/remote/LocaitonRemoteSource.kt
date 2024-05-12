package com.example.core.data.location.remote

import javax.inject.Inject

class LocationRemoteSource @Inject constructor(
    private val service: LocationService
) {
    suspend fun getAllProvince() = service.getAllProvince()

    suspend fun getAllCityByProvinceId(id: String) = service.getAllCityByProvinceId(id)

    suspend fun getAllDistrictByCityId(id: String) = service.getAllDistrictByCityId(id)

    suspend fun getAllVillageByDistrictId(id: String) = service.getAllVillageByDistrictId(id)
}