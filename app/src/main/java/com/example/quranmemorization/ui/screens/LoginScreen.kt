package com.example.quranmemorization.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.example.quranmemorization.data.api.RetrofitClient // Ditambahkan impor
import com.example.quranmemorization.data.api.ApiService
import com.example.quranmemorization.ui.theme.IslamicGold
import com.example.quranmemorization.ui.theme.IslamicGreen
import com.example.quranmemorization.ui.theme.IslamicWhite
import com.example.quranmemorization.ui.theme.QuranMemorizationTheme
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@Composable
fun LoginScreen(onLoginSuccess: (String) -> Unit = {}) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val apiService = RetrofitClient.apiService

    // Konfigurasi Keycloak (ganti dengan nilai Anda)
    val clientId = "setoran-mobile-dev" // Ganti dengan client_id dari Keycloak
    val clientSecret = "aqJp3xnXKudgC7RMOshEQP7ZoVKWzoSl" // Ganti dengan client_secret dari Keycloak
    val grantType = "password" // Untuk Resource Owner Password Credentials Grant

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(IslamicWhite)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Quran Memorization",
                style = MaterialTheme.typography.titleLarge,
                color = IslamicGreen,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp)),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    OutlinedTextField(
                        value = username,
                        onValueChange = { username = it },
                        label = { Text("Username") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = IslamicGreen,
                            unfocusedBorderColor = IslamicGold
                        )
                    )

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password") },
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = IslamicGreen,
                            unfocusedBorderColor = IslamicGold
                        )
                    )

                    AnimatedVisibility(
                        visible = showError,
                        enter = fadeIn(animationSpec = tween(500)),
                        exit = fadeOut(animationSpec = tween(500))
                    ) {
                        Text(
                            text = "Invalid credentials or network error",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.labelLarge,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                    }

                    Button(
                        onClick = {
                            scope.launch {
                                try {
                                    val response = apiService.login(
                                        username = username,
                                        password = password,
                                        clientId = clientId,
                                        clientSecret = clientSecret,
                                        grantType = grantType
                                    )
                                    // Simpan token (opsional, di sini hanya cetak untuk debug)
                                    println("Access Token: ${response.accessToken}")
                                    onLoginSuccess(username) // Lanjut ke MainScreen
                                    showError = false
                                } catch (e: Exception) {
                                    showError = true
                                    e.printStackTrace()
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = IslamicGreen),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Login", color = IslamicWhite, style = MaterialTheme.typography.labelLarge)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    QuranMemorizationTheme {
        LoginScreen()
    }
}