package com.example.core.di

import com.example.core.BuildConfig
import com.example.core.data.cart.CartRepository
import com.example.core.data.cart.remote.CartRemoteSource
import com.example.core.data.cart.remote.CartService
import com.example.core.data.kategori.remote.KategoriRemoteSource
import com.example.core.data.kategori.remote.KategoriService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CartModule {
    @Provides
    @Singleton
    fun provideCartRemote(
        builder: Retrofit.Builder
    ) = CartRemoteSource(
        service = builder
            .baseUrl(BuildConfig.BASE_URL_FAKE)
            .build()
            .create(CartService::class.java)
    )

    @Provides
    @Singleton
    fun provideCartRepository(
        remoteSource: CartRemoteSource
    ) = CartRepository(
        remoteSource
    )
}