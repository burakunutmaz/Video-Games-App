package com.example.projectvideogames.Api

import com.example.projectvideogames.Utils.Constants.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: NetworkClient by lazy{
        retrofit.create(NetworkClient::class.java)
    }

}