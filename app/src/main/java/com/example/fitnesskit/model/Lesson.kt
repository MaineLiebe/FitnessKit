package com.example.fitnesskit.model

import com.google.gson.annotations.SerializedName

data class Lesson(
    val name: String,
    val startTime: String,
    val endTime: String,
    val place: String,
    @SerializedName("coach_id")
    val coachId: String,
    val color: String,
    val date: String,
    val description: String,
)