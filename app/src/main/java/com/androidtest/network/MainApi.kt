package com.androidtest.network

import com.androidtest.model.MainModel
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET

interface MainApi {

    @GET("/e10651b2-5a36-4cd8-b284-6b2fb1f4da0e")
    fun getAppList(): Single<Response<List<MainModel>>>
}