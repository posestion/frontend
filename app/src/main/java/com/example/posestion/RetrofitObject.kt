package com.example.posestion

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitObject {

    private const val BASE_URL = "http://Posestion-env.eba-m8pmts6k.us-east-1.elasticbeanstalk.com"

    private val getRetrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val getRetrofitService : RetrofitService by lazy { getRetrofit.create(RetrofitService::class.java) }

}