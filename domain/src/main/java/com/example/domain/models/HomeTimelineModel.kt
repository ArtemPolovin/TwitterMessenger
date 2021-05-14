package com.example.domain.models

data class HomeTimelineModel(
    val name: String,
    val tweet: String,
    val nickname: String,
    val publishedTwitDate: String,
    val profileImageURL: String,
    val attachedImages: List<String>?,
    )