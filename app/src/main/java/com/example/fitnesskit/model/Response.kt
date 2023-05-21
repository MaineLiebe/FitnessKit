package com.example.fitnesskit.model

data class Response(
    val trainers: List<Coach>,
    val lessons: List<Lesson>
)
