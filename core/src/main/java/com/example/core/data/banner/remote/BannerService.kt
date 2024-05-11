package com.example.core.data.banner.remote

import com.example.core.model.response.banner.SingleBannerResponse
import retrofit2.Response
import retrofit2.http.GET

interface BannerService {
    @GET("banner")
    suspend fun getAllBanner(): Response<List<SingleBannerResponse>>
}