package com.example.myapplication.data.repository

import com.example.myapplication.data.api.ApiService

class Repository(private val apiHelper: ApiService) {
    suspend fun fetchNews(query:String , from:String, to:String) = apiHelper.fetchNews(query ,from,to)
//    suspend fun fetchNewsDetails(page:Int) = apiHelper.getPopularMovies(page)
}