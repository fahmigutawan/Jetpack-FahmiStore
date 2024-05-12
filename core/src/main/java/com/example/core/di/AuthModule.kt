package com.example.core.di

import android.content.SharedPreferences
import com.example.core.BuildConfig
import com.example.core.data.auth.AuthRepository
import com.example.core.data.auth.local.AuthLocalSource
import com.example.core.data.auth.remote.AuthRemoteSource
import com.example.core.data.auth.remote.AuthService
import com.example.core.data.product.remote.ProductRemoteSource
import com.example.core.room.MyRoomDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [BaseModule::class])
@InstallIn(SingletonComponent::class)
object AuthModule {
    @Provides
    @Singleton
    fun provideAuthLocal(
        pref: SharedPreferences
    ) = AuthLocalSource(
        pref
    )

    @Provides
    @Singleton
    fun provideAuthRemote(
        builder: Retrofit.Builder
    ) = AuthRemoteSource(
        service = builder
            .baseUrl(BuildConfig.BASE_URL)
            .build()
            .create(AuthService::class.java)
    )

    @Provides
    @Singleton
    fun provideAuthRepository(
        remoteSource: AuthRemoteSource,
        localSource: AuthLocalSource
    ) = AuthRepository(
        remote = remoteSource,
        local = localSource
    )
}