package com.example.quranmemorization

import android.content.Context
import androidx.core.content.edit
import com.example.quranmemorization.data.api.RetrofitClient
import kotlinx.coroutines.runBlocking

class TokenManager(context: Context) {
    private val prefs = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
    private val apiService = RetrofitClient.apiService

    fun saveTokens(accessToken: String, refreshToken: String, idToken: String) {
        prefs.edit {
            putString("access_token", accessToken)
            putString("refresh_token", refreshToken)
            putString("id_token", idToken)
        }
    }

    fun getAccessToken(): String? = prefs.getString("access_token", null)
    fun getRefreshToken(): String? = prefs.getString("refresh_token", null)
    fun getIdToken(): String? = prefs.getString("id_token", null)

    fun clearTokens() {
        prefs.edit {
            clear()
        }
    }

    fun refreshTokens(clientId: String, clientSecret: String): Boolean {
        val refreshToken = getRefreshToken() ?: return false
        return runBlocking {
            try {
                val response = apiService.refreshToken(
                    clientId = clientId,
                    clientSecret = clientSecret,
                    grantType = "refresh_token",
                    refreshToken = refreshToken
                )
                if (response.isSuccessful) {
                    response.body()?.let { authResponse ->
                        saveTokens(
                            accessToken = authResponse.access_token,
                            refreshToken = authResponse.refresh_token,
                            idToken = authResponse.id_token
                        )
                        true
                    } ?: false
                } else {
                    false
                }
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
    }
}