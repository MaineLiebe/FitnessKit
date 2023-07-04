package com.example.fitnesskit.repository

import com.example.fitnesskit.model.Response
import javax.inject.Inject

class MockTrainingRepository @Inject constructor(): TrainingRepository {
    override suspend fun getInfo(): Response? {
        return Response(emptyList(), emptyList())
    }
}