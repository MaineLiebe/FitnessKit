package com.example.fitnesskit.repository

import android.util.Log
import com.example.fitnesskit.api.RetrofitApi
import com.example.fitnesskit.model.Response
import javax.inject.Inject


class TrainingRepositoryImpl @Inject constructor(
    private val retrofitApi: RetrofitApi
) : TrainingRepository {
    override suspend fun getInfo(): Response? {
        return try {
            retrofitApi.getInfo(2)
        } catch (t: Throwable) {
            Log.d("", t.toString())
            null
        }
    }
}