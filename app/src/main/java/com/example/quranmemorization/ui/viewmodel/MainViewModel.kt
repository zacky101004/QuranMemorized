package com.example.quranmemorization.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quranmemorization.data.api.RetrofitClient
import com.example.quranmemorization.data.api.SetoranResponse
import com.example.quranmemorization.TokenManager // Tambahkan impor ini
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

class MainViewModel : ViewModel() {
    private val _setoranResponse = MutableStateFlow<SetoranResponse?>(null)
    val setoranResponse: StateFlow<SetoranResponse?> = _setoranResponse

    fun fetchSetoranData(context: Context) {
        viewModelScope.launch {
            try {
                val apiService = RetrofitClient.getSetoranApiService(context)
                val response = apiService.getSetoranSaya("Bearer ${context.getToken()}")
                if (response.isSuccessful) {
                    response.body()?.let { setoran ->
                        _setoranResponse.value = setoran
                    }
                } else {
                    println("Error fetching setoran: ${response.code()} - ${response.message()}")
                }
            } catch (e: HttpException) {
                println("HTTP Error: ${e.code()} - ${e.message()}")
            } catch (e: Exception) {
                println("Error fetching setoran: ${e.message}")
            }
        }
    }

    // Extension function untuk mendapatkan token dari context
    private fun Context.getToken(): String? {
        val tokenManager = TokenManager(this) // Sekarang seharusnya tidak error
        return tokenManager.getAccessToken()
    }
}