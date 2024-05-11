package com.example.core.di

import android.content.Context
import androidx.room.Room
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
    fun provideRoomDb(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context = context,
        klass = MyRoomDb::class.java,
        name = "fahmi-store-db"
    ).build()
}