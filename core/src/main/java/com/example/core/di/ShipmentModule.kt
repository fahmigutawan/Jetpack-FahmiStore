package com.example.core.di

import com.example.core.BuildConfig
import com.example.core.data.product.ProductRepository
import com.example.core.data.product.remote.ProductRemoteSource
import com.example.core.data.product.remote.ProductService
import com.example.core.data.shipment.ShipmentRepository
import com.example.core.data.shipment.remote.ShipmentRemoteSource
import com.example.core.data.shipment.remote.ShipmentService
import com.example.core.room.MyRoomDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [BaseModule::class])
@InstallIn(SingletonComponent::class)
object ShipmentModule {
    @Provides
    @Singleton
    fun provideShipmentRemote(
        builder: Retrofit.Builder
    ) = ShipmentRemoteSource(
        service = builder
            .baseUrl(BuildConfig.BASE_URL_FAKE)
            .build()
            .create(ShipmentService::class.java)
    )

    @Provides
    @Singleton
    fun provideShipmentRepository(
        remoteSource: ShipmentRemoteSource,
    ) = ShipmentRepository(remoteSource)
}