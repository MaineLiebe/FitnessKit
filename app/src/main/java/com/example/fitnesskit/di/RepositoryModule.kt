package com.example.fitnesskit.di

import com.example.fitnesskit.repository.MockTrainingRepository
import com.example.fitnesskit.repository.TrainingRepository
import com.example.fitnesskit.repository.TrainingRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindTrainingRepository(
        trainingRepositoryImpl: TrainingRepositoryImpl
    ): TrainingRepository
}