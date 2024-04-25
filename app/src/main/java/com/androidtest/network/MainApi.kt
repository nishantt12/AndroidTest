package com.androidtest.network

import com.androidtest.model.MainModel
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET

interface MainApi {

    @GET("/v1/509533c7-9a87-4edd-9db1-491c5ce48938")
    fun getAppList(): Single<Response<List<MainModel>>>
}