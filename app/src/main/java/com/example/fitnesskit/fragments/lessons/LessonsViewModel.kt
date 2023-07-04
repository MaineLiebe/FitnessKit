package com.example.fitnesskit.fragments.lessons

import android.icu.text.CaseMap
import android.icu.text.SimpleDateFormat
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitnesskit.api.ApiCore
import com.example.fitnesskit.api.RetrofitApi
import com.example.fitnesskit.model.*
import com.example.fitnesskit.repository.TrainingRepository
import com.example.fitnesskit.usecase.GetTrainingUseCase
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class LessonsViewModel @Inject constructor(
    private val getTrainingUseCase: GetTrainingUseCase
) : ViewModel() {

    private val _lessons: MutableLiveData<List<ListItem>> = MutableLiveData()
    val lessons: LiveData<List<ListItem>> get() = _lessons

    init {
        getInfo()
    }

    fun getInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = getTrainingUseCase.getTraining()
                _lessons.postValue(response)

        }
    }
}