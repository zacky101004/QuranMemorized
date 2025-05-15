package com.example.quranmemorization

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.rememberNavController // Tambahkan impor ini
import com.example.quranmemorization.ui.navigation.AppNavigation
import com.example.quranmemorization.ui.theme.QuranMemorizationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuranMemorizationTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController() // Buat instance NavHostController
                    AppNavigation(navController = navController) // Panggil dengan parameter navController
                }
            }
        }
    }
}