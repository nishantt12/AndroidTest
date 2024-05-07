package com.androidtest.repository

import android.util.Log
import com.androidtest.model.MainModel
import com.androidtest.network.MainApi
import com.androidtest.presistence.MainDao
import com.androidtest.presistence.MainModelEntity
import io.reactivex.Observable
import io.reactivex.Single
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
                Log.e("Name","Thread: ${Thread.currentThread().name}1")
                it.isNotEmpty()
            }
            .doOnComplete {
                Log.e("Complete","Testing complete 2")

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
                Log.e("Name","Thread: ${Thread.currentThread().name}")
                it.body()!!
            }
            .onErrorResumeNext {
                Log.e("Name","Thread: ${Thread.currentThread().name}")
                it.printStackTrace()
                Single.just(emptyList())
            }
            .filter {
                Log.e("Name","Thread: ${Thread.currentThread()}")

                it.isNotEmpty()
            }
            .doOnComplete {
                Log.e("Complete","Testing complete 3")

            }
            .toObservable()
            .doOnNext {
                if (it.isNotEmpty())
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


    private fun MainModelEntity.toMainModel(): MainModel {
        return MainModel(
            id,
            title,
            description,
            imageSrc,
        )
    }


    private fun MainModel.toMainModelEntity(): MainModelEntity {
        return MainModelEntity(
            id,
            title,
            description,
            imageSrc,
        )
    }

}
