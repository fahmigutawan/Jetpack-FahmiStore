package com.example.core.di

import com.example.core.BuildConfig
import com.example.core.data.kategori.remote.KategoriRemoteSource
import com.example.core.data.kategori.remote.KategoriService
import com.example.core.data.kategori.KategoriRepository
import com.example.core.data.kategori.local.KategoriDao
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
object KategoriModule {
    @Provides
    @Singleton
    fun provideKategoriRemote(
        builder: Retrofit.Builder
    ) = KategoriRemoteSource(
        service = builder
            .baseUrl(BuildConfig.BASE_URL)
            .build()
            .create(KategoriService::class.java)
    )

    @Provides
    @Singleton
    fun provideKategoriRepository(
        remoteSource: KategoriRemoteSource,
        roomDb: MyRoomDb
    ) = KategoriRepository(roomDb.kategoriDao(), remoteSource)
}