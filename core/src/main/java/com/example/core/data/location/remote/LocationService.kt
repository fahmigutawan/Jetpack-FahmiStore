package com.example.core.data.location.remote

import com.example.core.model.response.location.SingleCityResponse
import com.example.core.model.response.location.SingleDistrictResponse
import com.example.core.model.response.location.SingleProvinceResponse
import com.example.core.model.response.location.SingleVillageResponse
import com.example.core.util.Resource
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface LocationService {
    @GET("provinces.json")
    suspend fun getAllProvince(): Response<List<SingleProvinceResponse>>

    @GET("regencies/{province_id}.json")
    suspend fun getAllCityByProvinceId(
        @Path("province_id") provinceId: String
    ): Response<List<SingleCityResponse>>

    @GET("districts/{city_id}.json")
    suspend fun getAllDistrictByCityId(
        @Path("city_id") cityId: String
    ): Response<List<SingleDistrictResponse>>

    @GET("villages/{district_id}.json")
    suspend fun getAllVillageByDistrictId(
        @Path("district_id") districtId: String
    ): Response<List<SingleVillageResponse>>
}