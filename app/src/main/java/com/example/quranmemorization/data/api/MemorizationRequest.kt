package com.example.quranmemorization.data.api

data class MemorizationRequest(
    val username: String,
    val surahName: String,
    val date: String
)