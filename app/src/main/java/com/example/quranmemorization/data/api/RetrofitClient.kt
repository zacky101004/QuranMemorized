package com.example.quranmemorization.data.api

import android.content.Context
import com.example.quranmemorization.TokenManager
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val KC_BASE_URL = "https://id.tif.uin-suska.ac.id" // Ganti dengan URL Keycloak Anda (misalnya https://your-keycloak-domain.com/auth/)
    private const val API_BASE_URL = "https://api.tif.uin-suska.ac.id/setoran-dev/v1/" // Ganti dengan URL API setoran Anda (misalnya https://your-api-domain.com/)

    val apiService: ApiService by lazy {
        val logging = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
        Retrofit.Builder()
            .baseUrl(KC_BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    fun getSetoranApiService(context: Context): ApiService {
        val tokenManager = TokenManager(context)
        val logging = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
        val client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original = chain.request()
                val token = tokenManager.getAccessToken()
                val request = if (token != null) {
                    original.newBuilder()
                        .header("Authorization", "Bearer $token")
                        .build()
                } else {
                    original
                }
                val response = chain.proceed(request)
                if (response.code == 401) {
                    val refreshed = tokenManager.refreshTokens(
                        clientId = "setoran-mobile-dev", // Ganti dengan client_id Anda
                        clientSecret = "aqJp3xnXKudgC7RMOshEQP7ZoVKWzoSl" // Ganti dengan client_secret Anda
                    )
                    if (refreshed) {
                        val newToken = tokenManager.getAccessToken()
                        if (newToken != null) {
                            val newRequest = original.newBuilder()
                                .header("Authorization", "Bearer $newToken")
                                .build()
                            return@addInterceptor chain.proceed(newRequest)
                        }
                    }
                }
                response
            }
            .addInterceptor(logging)
            .build()
        return Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}