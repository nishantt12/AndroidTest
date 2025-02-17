package com.androidtest.di

import com.androidtest.network.MainApi
import com.androidtest.presistence.MainDao
import com.androidtest.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    @ViewModelScoped
    fun provideMainRepository(
        mainDao: MainDao,
        mainApi: MainApi,
    ): MainRepository {
        return MainRepository(mainDao, mainApi)
    }

}
