package com.example.core.di

import com.example.core.BuildConfig
import com.example.core.data.banner.BannerRepository
import com.example.core.data.banner.remote.BannerRemoteSource
import com.example.core.data.banner.remote.BannerService
import com.example.core.data.kategori.KategoriRepository
import com.example.core.data.kategori.remote.KategoriRemoteSource
import com.example.core.data.kategori.remote.KategoriService
import com.example.core.data.location.LocationRepository
import com.example.core.data.location.remote.LocationRemoteSource
import com.example.core.data.location.remote.LocationService
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
object LocationModule {
    @Provides
    @Singleton
    fun provideLocationRemote(
        builder: Retrofit.Builder
    ) = LocationRemoteSource(
        service = builder
            .baseUrl(BuildConfig.BASE_URL_LOCATION)
            .build()
            .create(LocationService::class.java)
    )

    @Provides
    @Singleton
    fun provideLocationRepository(
        remoteSource: LocationRemoteSource,
        roomDb: MyRoomDb
    ) = LocationRepository(
        roomDb.locationDao(),
        remoteSource
    )
}