package com.example.fitnesskit.di

import com.example.fitnesskit.usecase.GetTrainingUseCase
import com.example.fitnesskit.usecase.GetTrainingUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class UseCaseModule {

    @Binds
    abstract fun bindGetTrainingUseCase(
        getTrainingUseCaseImpl: GetTrainingUseCaseImpl
    ): GetTrainingUseCase

}