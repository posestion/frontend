package com.example.posestion

import com.example.posestion.connection.RetrofitAPI
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitObject {

    private const val BASE_URL = "http://Posestion-env.eba-m8pmts6k.us-east-1.elasticbeanstalk.com"

    private val getRetrofit by lazy {

        val clientBuilder = OkHttpClient.Builder()

        clientBuilder.readTimeout(30, TimeUnit.SECONDS) // 읽기 타임아웃: 30초
        clientBuilder.writeTimeout(30, TimeUnit.SECONDS) // 쓰기 타임아웃: 30초

        val client = clientBuilder.build()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val getRetrofitService : RetrofitAPI by lazy { getRetrofit.create(RetrofitAPI::class.java) }

}