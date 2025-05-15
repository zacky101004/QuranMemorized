package com.example.quranmemorization.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.quranmemorization.R
import com.example.quranmemorization.data.api.RetrofitClient
import com.example.quranmemorization.ui.theme.IslamicGold
import com.example.quranmemorization.ui.theme.IslamicGreen
import com.example.quranmemorization.ui.theme.IslamicWhite
import com.example.quranmemorization.ui.theme.QuranMemorizationTheme
import kotlinx.coroutines.launch
import com.example.quranmemorization.TokenManager

@Composable
fun LoginScreen(onLoginSuccess: (String) -> Unit = {}) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val apiService = RetrofitClient.apiService
    val context = LocalContext.current
    val tokenManager = remember { TokenManager(context) }

    val clientId = "setoran-mobile-dev" // Ganti dengan client_id Anda
    val clientSecret = "aqJp3xnXKudgC7RMOshEQP7ZoVKWzoSl" // Ganti dengan client_secret Anda
    val grantType = "password"

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(IslamicWhite, IslamicGreen.copy(alpha = 0.1f))
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp)
                .padding(top = 64.dp, bottom = 32.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo
            Image(
                painter = painterResource(id = R.drawable.img), // Ganti dengan nama file logo Anda
                contentDescription = "App Logo",
                modifier = Modifier
                    .size(120.dp)
                    .padding(bottom = 16.dp)
            )

            // Judul dan Subjudul
            Text(
                text = "Quran Memorization",
                style = MaterialTheme.typography.titleLarge.copy(fontSize = 28.sp),
                color = IslamicGreen,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Setoran Hafalan Mahasiswa",
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp),
                color = IslamicGold,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp)),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
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
                                        clientId = clientId,
                                        clientSecret = clientSecret,
                                        grantType = grantType,
                                        username = username,
                                        password = password,
                                        scope = "openid profile email"
                                    )
                                    if (response.isSuccessful) {
                                        response.body()?.let { authResponse ->
                                            tokenManager.saveTokens(
                                                accessToken = authResponse.access_token,
                                                refreshToken = authResponse.refresh_token,
                                                idToken = authResponse.id_token
                                            )
                                            onLoginSuccess(username)
                                            showError = false
                                        }
                                    } else {
                                        showError = true
                                    }
                                } catch (e: Exception) {
                                    showError = true
                                    e.printStackTrace()
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
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