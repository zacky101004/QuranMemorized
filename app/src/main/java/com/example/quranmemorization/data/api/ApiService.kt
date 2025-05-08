package com.example.quranmemorization.data.api

import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @FormUrlEncoded
    @POST("realms/dev/protocol/openid-connect/token")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("grant_type") grantType: String
    ): AuthResponse

    @POST("submit-memorization")
    suspend fun submitMemorization(@Body request: MemorizationRequest): MemorizationResponse

    @GET("memorization-list")
    suspend fun getMemorizationList(): List<MemorizationResponse>
}