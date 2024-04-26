package com.androidtest.repository

import android.util.Log
import com.androidtest.model.MainModel
import com.androidtest.network.MainApi
import com.androidtest.presistence.MainDao
import com.androidtest.presistence.MainModelEntity
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val mainDao: MainDao,
    private val mainApi: MainApi,
) {

    fun getData(): Observable<List<MainModel>> {
        return Observable.concatArray(
            getDataFromDb(),
            getDataFromApi()
        )
    }

    private fun getDataFromDb(): Observable<List<MainModel>> {
        return mainDao.getTalaList()
            .filter {
                it.isNotEmpty()
            }
            .map {
                val newList = mutableListOf<MainModel>()
                it.forEach {
                    newList.add(it.toMainModel())
                }
                newList.toList()
            }
            .toObservable()
    }

    private fun getDataFromApi(): Observable<List<MainModel>> {
        return mainApi.getAppList()
            .map {
                it.body()!!
            }
            .toObservable()
            .doOnNext {
                storeDataInDb(it)
            }
    }

    private fun storeDataInDb(mainModel: List<MainModel>) {
        val entity = transform(mainModel)
        Observable.fromCallable { mainDao.insertTalaList(entity) }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe {
                Log.d("database", "Inserted")
            }

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
