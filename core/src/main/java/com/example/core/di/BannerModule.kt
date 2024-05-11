package com.example.core.di

import com.example.core.BuildConfig
import com.example.core.data.banner.BannerRepository
import com.example.core.data.banner.remote.BannerRemoteSource
import com.example.core.data.banner.remote.BannerService
import com.example.core.data.kategori.KategoriRepository
import com.example.core.data.kategori.remote.KategoriRemoteSource
import com.example.core.data.kategori.remote.KategoriService
import com.example.core.room.MyRoomDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(
    includes = [
        BaseModule::class
    ]
)
@InstallIn(SingletonComponent::class)
object BannerModule {
    @Provides
    @Singleton
    fun provideBannerRemote(
        builder: Retrofit.Builder
    ) = BannerRemoteSource(
        service = builder
            .baseUrl(BuildConfig.BASE_URL_FAKE)
            .build()
            .create(BannerService::class.java)
    )

    @Provides
    @Singleton
    fun provideBannerRepository(
        remoteSource: BannerRemoteSource,
        roomDb: MyRoomDb
    ) = BannerRepository(roomDb.bannerDao(), remoteSource)
}