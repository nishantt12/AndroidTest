package com.androidtest.di

import android.app.Application
import androidx.room.Room
import com.androidtest.presistence.AppDatabase
import com.androidtest.presistence.MainDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object PersistentModule {

    @Provides
    @Singleton
    fun provideAppDatabase(application: Application): AppDatabase {
        return Room
            .databaseBuilder(application, AppDatabase::class.java, "tala_database")
            .build()

    }

    @Provides
    @Singleton
    fun provideDao(appDatabase: AppDatabase): MainDao{
        return  appDatabase.mainDao()
    }


}