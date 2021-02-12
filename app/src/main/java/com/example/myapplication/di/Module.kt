package com.example.myapplication.di

import com.example.myapplication.BuildConfig
import com.example.myapplication.data.api.ApiService
import com.example.myapplication.data.repository.Repository
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException


public const val BASE_URL = "https://newsapi.org/"

val generalModule = module {
    single { Repository(get()) }
    single { provideOkHttpClient() }
    single { provideRetrofit(get()) }
    single { provideApiService(get()) }
}


private fun provideOkHttpClient() :  OkHttpClient{
    val logging = HttpLoggingInterceptor()
    logging.level = HttpLoggingInterceptor.Level.BODY

    val httpClient = OkHttpClient.Builder()
    httpClient.addInterceptor(logging);
    httpClient.addInterceptor(object : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response? {
            var request: Request =
                chain.request().newBuilder()
                    .addHeader("Content-Type" , "application/json")
                    .build()
            val url = request.url().newBuilder()
                .addQueryParameter("apiKey", BuildConfig.AGENT_API_KEY)
//                .addQueryParameter("language", "en-US")
                .build()
            request = request.newBuilder().url(url).build()
            return chain.proceed(request)
        }
    })
    return httpClient.build()
}
private fun provideRetrofit(httpClient:OkHttpClient): Retrofit{
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(httpClient)

        .build() //Doesn't require the adapter
}
private fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)
