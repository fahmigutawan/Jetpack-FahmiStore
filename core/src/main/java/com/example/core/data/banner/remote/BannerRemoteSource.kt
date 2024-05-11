package com.example.core.data.banner.remote

import javax.inject.Inject

class BannerRemoteSource @Inject constructor(
    private val service: BannerService
){
    suspend fun getAllBanner() = service.getAllBanner()
}