package com.androidtest.repository

import com.androidtest.model.MainModel
import com.androidtest.network.MainApi
import com.androidtest.presistence.MainDao
import com.androidtest.presistence.MainModelEntity
import io.reactivex.Single
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val ioDispatcher: CoroutineDispatcher,
    private val mainDao: MainDao,
    private val mainApi: MainApi,
) {

    fun fetchApplicationList(
    ): Single<List<MainModel>?> {
        val list =
            mainDao.getTalaList()
                .flattenAsObservable { it }
                .map {
                    it.toMainModel()
                }
                .toList()
                .onErrorResumeNext {
                    mainApi.getAppList().map {
                        it.body()
                    }
                }



        return list
    }


    fun MainModelEntity.toMainModel(): MainModel {
        return MainModel(
            id,
            title,
            description,
            imageSrc,
        )
    }

}
