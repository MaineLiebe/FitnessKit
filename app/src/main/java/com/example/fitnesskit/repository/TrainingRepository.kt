package com.example.fitnesskit.repository

import com.example.fitnesskit.model.Response

interface TrainingRepository {
    suspend fun getInfo(): Response?
}