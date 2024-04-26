package com.androidtest.network

import com.androidtest.model.MainModel
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET

interface MainApi {

    @GET("/v1/9f5b71fc-555e-4090-8da6-9ea718ca0cba")
    fun getAppList(): Single<Response<List<MainModel>>>
}