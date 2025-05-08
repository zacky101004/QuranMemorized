package com.example.quranmemorization.data.api

import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName("access_token")
    val accessToken: String,

    @SerializedName("token_type")
    val tokenType: String,

    @SerializedName("id_token")
    val idToken: String,

    @SerializedName("not-before-policy")
    val notBeforePolicy: Long,

    @SerializedName("session_state")
    val sessionState: String,

    val scope: String
)