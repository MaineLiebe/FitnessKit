package com.example.fitnesskit.model

data class UiLesson(
    val name: String,
    val startTime: String,
    val startTimeInMills: Long,
    val endTime: String,
    val place: String,
    val coachId: String,
    val color: String,
    val time: String,
    val coachName: String,
    val dateMills: Long
) : ListItem