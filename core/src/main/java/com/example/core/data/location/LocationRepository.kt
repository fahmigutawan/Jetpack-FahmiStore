package com.example.core.data.location

import com.example.core.data.location.local.LocationDao
import com.example.core.data.location.remote.LocationRemoteSource
import com.example.core.model.entity.CityEntity
import com.example.core.model.entity.DistrictEntity
import com.example.core.model.entity.ProvinceEntity
import com.example.core.model.entity.VillageEntity
import com.example.core.model.response.location.SingleCityResponse
import com.example.core.model.response.location.SingleDistrictResponse
import com.example.core.model.response.location.SingleProvinceResponse
import com.example.core.model.response.location.SingleVillageResponse
import com.example.core.util.getResponseWithTransaction
import javax.inject.Inject

class LocationRepository @Inject constructor(
    private val local: LocationDao,
    private val remote: LocationRemoteSource
) {
    fun getAllProvince() = getResponseWithTransaction(
        onRetrieved = {
            local.insertAllProvince(
                it.map {
                    ProvinceEntity(
                        id = it.id,
                        name = it.name
                    )
                }
            )
        },
        localCall = {
            local.getAllProvince()
        },
        mapper = {
            it.map {
                SingleProvinceResponse(
                    id = it.id,
                    name = it.name
                )
            }
        }
    ) {
        remote.getAllProvince()
    }

    fun getAllCityByProvinceId(id: String) = getResponseWithTransaction(
        onRetrieved = {
            local.insertAllCity(it.map {
                CityEntity(
                    id = it.id,
                    province_id = it.province_id,
                    name = it.name
                )
            })
        },
        localCall = {
            local.getAllCityByProvinceId(id)
        },
        mapper = {
            it.map {
                SingleCityResponse(
                    id = it.id,
                    province_id = it.province_id,
                    name = it.name
                )
            }
        }
    ) {
        remote.getAllCityByProvinceId(id)
    }

    fun getAllDistrictByCityId(id: String) = getResponseWithTransaction(
        onRetrieved = {
            local.insertAllDistrict(it.map {
                DistrictEntity(
                    id = it.id,
                    regency_id = it.regency_id,
                    name = it.name
                )
            })
        },
        localCall = {
            local.getAllDistrictByCityId(id)
        },
        mapper = {
            it.map {
                SingleDistrictResponse(
                    id = it.id,
                    regency_id = it.regency_id,
                    name = it.name
                )
            }
        }
    ) {
        remote.getAllDistrictByCityId(id)
    }

    fun getAllVillageByDistrictId(id: String) = getResponseWithTransaction(
        onRetrieved = {
            local.insertAllVillage(it.map {
                VillageEntity(
                    id = it.id,
                    district_id = it.district_id,
                    name = it.name
                )
            })
        },
        localCall = {
            local.getAllVillageByDistrictId(id)
        },
        mapper = {
            it.map {
                SingleVillageResponse(
                    id = it.id,
                    district_id = it.district_id,
                    name = it.name
                )
            }
        }
    ) {
        remote.getAllVillageByDistrictId(id)
    }
}