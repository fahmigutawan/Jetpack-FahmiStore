package com.example.core.data.banner

import com.example.core.data.banner.local.BannerDao
import com.example.core.data.banner.remote.BannerRemoteSource
import com.example.core.model.entity.BannerEntity
import com.example.core.model.response.banner.SingleBannerResponse
import com.example.core.util.getResponseWithTransaction
import javax.inject.Inject

class BannerRepository @Inject constructor(
    private val localSource: BannerDao,
    private val remoteSource: BannerRemoteSource
){
    fun getAllBanner() = getResponseWithTransaction(
        onRetrieved = {
            localSource.deleteAllBanner()
            localSource.insertAllBanner(it.map {
                BannerEntity(
                    id = it.id,
                    image = it.image
                )
            })
        },
        localCall = { localSource.getAllBanner() },
        mapper = {
            it.map {
                SingleBannerResponse(
                    id = it.id,
                    image = it.image
                )
            }
        }
    ){
        remoteSource.getAllBanner()
    }
}
