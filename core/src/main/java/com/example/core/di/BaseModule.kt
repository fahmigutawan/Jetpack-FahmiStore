package com.example.core.di

import android.content.Context
import androidx.room.Room
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.example.core.room.MyRoomDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class BaseModule {
    @Provides
    @Singleton
    fun provideRetrofitClient() = Retrofit
        .Builder()
        .addConverterFactory(GsonConverterFactory.create())

    @Provides
    @Singleton
    fun provideEncryptedSharedPreferences(
        @ApplicationContext context: Context
    ) = EncryptedSharedPreferences.create(
        "fahmi-store-pref",
        MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    @Provides
    @Singleton
    fun provideRoomDb(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context = context,
        klass = MyRoomDb::class.java,
        name = "fahmi-store-db"
    ).build()
}