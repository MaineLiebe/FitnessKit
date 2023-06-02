package com.example.fitnesskit.model

import com.google.gson.annotations.SerializedName

data class Coach (
    val id: String,
    val name: String,
    @SerializedName("image_url")
    val imageUrl: String
        )
