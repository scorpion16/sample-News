package com.example.myapplication.data.api

import com.example.myapplication.data.model.News
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("v2/everything")
    suspend fun fetchNews(
        @Query("q") querySearch:String,
        @Query("from") from:String,
        @Query("to") to:String
    ): News
}