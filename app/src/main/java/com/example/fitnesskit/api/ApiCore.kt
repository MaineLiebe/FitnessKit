package com.example.fitnesskit.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object ApiCore {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://olimpia.fitnesskit-admin.ru")
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(RetrofitApi::class.java)
}