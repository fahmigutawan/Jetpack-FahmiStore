package com.example.core.di

import com.example.core.BuildConfig
import com.example.core.data.product.ProductRepository
import com.example.core.data.product.local.ProductDao
import com.example.core.data.product.remote.ProductRemoteSource
import com.example.core.data.product.remote.ProductService
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
object ProductModule {
    @Provides
    @Singleton
    fun provideProductRemote(
        builder: Retrofit.Builder
    ) = ProductRemoteSource(
        service = builder
            .baseUrl(BuildConfig.BASE_URL)
            .build()
            .create(ProductService::class.java)
    )

    @Provides
    @Singleton
    fun provideProductRepository(
        remoteSource: ProductRemoteSource,
        roomDb: MyRoomDb
    ) = ProductRepository(roomDb.productDao(), remoteSource)
}