package com.example.flowpractice.data

import retrofit2.http.GET

interface GankApi {

//    https://gank.io/api/v2/data/category/Girl/type/Girl/page/1/count/10
    @GET("data/category/Girl/type/Girl/page/1/count/10")
    suspend fun getGirl()

}