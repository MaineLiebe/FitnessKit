package com.example.fitnesskit.usecase

import com.example.fitnesskit.model.ListItem

interface GetTrainingUseCase {
    suspend fun getTraining(): List<ListItem>
}