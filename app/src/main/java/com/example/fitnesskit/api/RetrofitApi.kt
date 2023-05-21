package com.example.fitnesskit.api

import com.example.fitnesskit.model.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitApi {
    @GET("/schedule/get_v3/")
    suspend fun getInfo(@Query("club_id") id: Int): Response
}