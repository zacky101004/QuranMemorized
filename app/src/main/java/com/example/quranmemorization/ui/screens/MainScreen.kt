package com.example.quranmemorization.ui.screens

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.quranmemorization.TokenManager
import com.example.quranmemorization.ui.theme.IslamicGold
import com.example.quranmemorization.ui.theme.IslamicGreen
import com.example.quranmemorization.ui.theme.IslamicTeal
import com.example.quranmemorization.ui.theme.IslamicWhite
import com.example.quranmemorization.ui.theme.QuranMemorizationTheme
import com.example.quranmemorization.ui.viewmodel.MainViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.graphicsLayer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(username: String, onLogout: () -> Unit, viewModel: MainViewModel = viewModel()) {
    val setoranState by viewModel.setoranResponse.collectAsState()
    val context = LocalContext.current
    val tokenManager = remember { TokenManager(context) }

    LaunchedEffect(Unit) {
        viewModel.fetchSetoranData(context)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Alquran Memorization",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = IslamicWhite
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                ),
                modifier = Modifier
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(IslamicGreen, IslamicTeal, IslamicGold)
                        )
                    )
                    .padding(horizontal = 16.dp),
                actions = {
                    val rotation by rememberInfiniteTransition().animateFloat(
                        initialValue = 0f,
                        targetValue = 360f,
                        animationSpec = infiniteRepeatable(
                            animation = tween(durationMillis = 2000, easing = LinearEasing),
                            repeatMode = RepeatMode.Restart
                        )
                    )
                    IconButton(onClick = {
                        tokenManager.clearTokens()
                        onLogout()
                    }) {
                        Icon(
                            imageVector = Icons.Default.ExitToApp,
                            contentDescription = "Logout",
                            tint = IslamicWhite,
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(IslamicWhite, Color.White.copy(alpha = 0.9f))
                    )
                )
                .padding(paddingValues)
        ) {
            if (setoranState == null) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else if (setoranState?.response == true) {
                val data = setoranState!!.data
                Log.d("MainScreen", "Jumlah item setoran: ${data.setoran.detail.size}")
                data.setoran.detail.forEachIndexed { index, item ->
                    Log.d("MainScreen", "Item $index: ${item.nama}, Sudah Setor: ${item.sudah_setor}")
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    // Informasi Mahasiswa dengan latar belakang hijau
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(20.dp))
                            .background(IslamicGreen) // Ubah latar belakang menjadi IslamicGreen
                            .border(2.dp, IslamicGold, RoundedCornerShape(20.dp)),
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.Transparent) // Set containerColor ke Transparent agar background IslamicGreen terlihat
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "Selamat Datang, $username! 🌙",
                                fontSize = 26.sp,
                                fontWeight = FontWeight.Bold,
                                color = IslamicWhite, // Ubah warna teks menjadi IslamicWhite agar kontras
                                modifier = Modifier.padding(bottom = 12.dp)
                            )
                            InfoText(label = "Nama", value = data.info.nama)
                            InfoText(label = "NIM", value = data.info.nim)
                            InfoText(label = "Email", value = data.info.email)
                            InfoText(label = "Angkatan", value = data.info.angkatan)
                            InfoText(label = "Semester", value = data.info.semester.toString())
                            InfoText(label = "Dosen Pembimbing", value = data.info.dosen_pa.nama)
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Progres dengan CircularProgressIndicator
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(bottom = 16.dp)
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.size(80.dp)
                        ) {
                            CircularProgressIndicator(
                                progress = data.setoran.info_dasar.persentase_progres_setor / 100f,
                                modifier = Modifier.size(70.dp),
                                color = IslamicGreen,
                                trackColor = IslamicTeal.copy(alpha = 0.3f),
                                strokeWidth = 6.dp
                            )
                            Text(
                                text = "${data.setoran.info_dasar.persentase_progres_setor}%",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                color = IslamicGreen
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Progres Hafalan",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = IslamicGreen
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    Divider(
                        color = IslamicGold.copy(alpha = 0.5f),
                        thickness = 1.dp,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )

                    Text(
                        text = "Daftar Setoran",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = IslamicGreen,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    AnimatedVisibility(
                        visible = true,
                        enter = fadeIn(animationSpec = tween(1000)),
                        exit = fadeOut(animationSpec = tween(500))
                    ) {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(1),
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            items(data.setoran.detail) { item ->
                                val scale by animateFloatAsState(
                                    targetValue = if (item.sudah_setor) 1.05f else 1f,
                                    animationSpec = tween(durationMillis = 200)
                                )
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(16.dp))
                                        .background(Color.White.copy(alpha = 0.8f))
                                        .border(1.dp, IslamicGold, RoundedCornerShape(16.dp))
                                        .scale(scale)
                                        .graphicsLayer { shadowElevation = 8f },
                                    colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .padding(16.dp)
                                            .fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(
                                                text = item.nama,
                                                fontSize = 18.sp,
                                                fontWeight = FontWeight.Medium,
                                                color = IslamicGreen
                                            )
                                            Text(
                                                text = "Label: ${item.label}",
                                                fontSize = 14.sp,
                                                color = IslamicTeal
                                            )
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Icon(
                                                    imageVector = if (item.sudah_setor) Icons.Default.CheckCircle else Icons.Default.Close,
                                                    contentDescription = null,
                                                    tint = if (item.sudah_setor) IslamicGreen else Color.Red,
                                                    modifier = Modifier.size(20.dp)
                                                )
                                                Spacer(modifier = Modifier.width(6.dp))
                                                Text(
                                                    text = if (item.sudah_setor) "Sudah Setor" else "Belum Setor",
                                                    fontSize = 14.sp,
                                                    color = if (item.sudah_setor) IslamicGreen else Color.Red
                                                )
                                            }
                                            item.info_setoran?.let {
                                                Text(
                                                    text = "Tanggal: ${it.tgl_setoran}",
                                                    fontSize = 12.sp,
                                                    color = IslamicTeal
                                                )
                                                Text(
                                                    text = "Dosen: ${it.dosen_yang_mengesahkan.nama}",
                                                    fontSize = 12.sp,
                                                    color = IslamicTeal
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                Text(
                    text = "Gagal memuat data: ${setoranState?.message}",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

@Composable
fun InfoText(label: String, value: String) {
    Text(
        text = "$label: $value",
        fontSize = 16.sp,
        color = IslamicWhite, // Ubah warna teks menjadi IslamicWhite agar kontras dengan latar belakang IslamicGreen
        modifier = Modifier.padding(vertical = 4.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    QuranMemorizationTheme {
        MainScreen(username = "Muhammad Zacky", onLogout = {})
    }
}