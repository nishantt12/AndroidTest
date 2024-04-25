package com.androidtest.repository

import android.util.Log
import com.androidtest.model.MainModel
import com.androidtest.network.MainApi
import com.androidtest.presistence.MainDao
import com.androidtest.presistence.MainModelEntity
import com.androidtest.ui.main.MainViewModel
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
                    Log.e("database", "${it.title}")
                    it.toMainModel()
                }
                .toList()
                .onErrorResumeNext {
                    mainApi.getAppList().map {
                        Log.e("mapper", "is it null $it")
                        val entity = transform(it.body())
                        mainDao.insertTalaList(entity)
                        it.body()
                    }
                }



        return list
    }

    private fun transform(mainModelList: List<MainModel>?): List<MainModelEntity> {
        val mainUiModelList = mutableListOf<MainModelEntity>()

        mainModelList?.forEach {
            mainUiModelList.add(
                it.toMainModelEntity()
            )
        }
        return mainUiModelList
    }


    fun MainModelEntity.toMainModel(): MainModel {
        return MainModel(
            id,
            title,
            description,
            imageSrc,
        )
    }


    fun MainModel.toMainModelEntity(): MainModelEntity {
        return MainModelEntity(
            id,
            title,
            description,
            imageSrc,
        )
    }

}
