package com.example.quranmemorization.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.quranmemorization.ui.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailSetoranScreen(
    category: String,
    navController: NavController,
    viewModel: MainViewModel
) {
    val setoranState by viewModel.setoranResponse.collectAsState()
    val context = LocalContext.current

    // Warna kontras untuk DetailScreen
    val primaryGreen = Color(0xFF2E7D32)       // Hijau gelap
    val secondaryGreen = Color(0xFF4CAF50)     // Hijau medium
    val lightGreen = Color(0xFF81C784)         // Hijau terang
    val accentGreen = Color(0xFF1B5E20)        // Hijau sangat gelap
    val backgroundGreen = Color(0xFFE8F5E8)    // Hijau sangat muda untuk background
    val pureWhite = Color.White
    val softWhite = Color(0xFFFAFAFA)

    // Fungsi untuk memformat tanggal dari YYYY-MM-DD ke DD-MM-YYYY
    fun formatDate(dateString: String): String {
        return try {
            val parts = dateString.split("-")
            if (parts.size == 3) {
                "${parts[2]}-${parts[1]}-${parts[0]}"
            } else {
                dateString // Return original jika format tidak sesuai
            }
        } catch (e: Exception) {
            dateString // Return original jika ada error
        }
    }

    LaunchedEffect(Unit) {
        viewModel.fetchSetoranData(context)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = category,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = pureWhite
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                ),
                modifier = Modifier
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(primaryGreen, accentGreen)
                        )
                    )
                    .padding(horizontal = 16.dp),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Kembali",
                            tint = pureWhite
                        )
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = Color.Transparent,
                modifier = Modifier.background(
                    brush = Brush.verticalGradient(
                        colors = listOf(primaryGreen, accentGreen)
                    )
                )
            ) {
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate("home") },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Beranda", tint = pureWhite) },
                    label = { Text("Beranda", color = pureWhite, fontWeight = FontWeight.Medium) }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate("profile") },
                    icon = { Icon(Icons.Default.Person, contentDescription = "Profil", tint = pureWhite) },
                    label = { Text("Profil", color = pureWhite, fontWeight = FontWeight.Medium) }
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(backgroundGreen, softWhite)
                    )
                )
                .padding(paddingValues)
        ) {
            if (setoranState == null) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = primaryGreen
                )
            } else if (setoranState?.response == true) {
                val data = setoranState!!.data
                val filteredSetoran = data.setoran.detail.filter {
                    it.label.trim().replace(" ", "").equals(
                        category.trim().replace(" ", ""),
                        ignoreCase = true
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    AnimatedVisibility(
                        visible = true,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        if (filteredSetoran.isEmpty()) {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                colors = CardDefaults.cardColors(containerColor = pureWhite),
                                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                                shape = RoundedCornerShape(16.dp)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .padding(24.dp)
                                        .fillMaxWidth(),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Info,
                                        contentDescription = null,
                                        tint = lightGreen,
                                        modifier = Modifier.size(48.dp)
                                    )
                                    Spacer(modifier = Modifier.height(16.dp))
                                    Text(
                                        text = "Tidak ada data untuk kategori $category",
                                        fontSize = 16.sp,
                                        color = primaryGreen,
                                        fontWeight = FontWeight.Medium,
                                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                                    )
                                }
                            }
                        } else {
                            LazyVerticalGrid(
                                columns = GridCells.Fixed(1),
                                verticalArrangement = Arrangement.spacedBy(16.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                items(filteredSetoran) { item ->
                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clip(RoundedCornerShape(16.dp))
                                            .background(pureWhite)
                                            .border(2.dp, lightGreen, RoundedCornerShape(16.dp)),
                                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                                        colors = CardDefaults.cardColors(containerColor = pureWhite)
                                    ) {
                                        Column(
                                            modifier = Modifier
                                                .padding(20.dp)
                                                .fillMaxWidth()
                                        ) {
                                            // Header dengan nama
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Text(
                                                    text = item.nama,
                                                    fontSize = 18.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = accentGreen,
                                                    modifier = Modifier.weight(1f)
                                                )
                                                Icon(
                                                    imageVector = if (item.sudah_setor) Icons.Default.CheckCircle else Icons.Default.Close,
                                                    contentDescription = null,
                                                    tint = if (item.sudah_setor) secondaryGreen else Color(0xFFE57373),
                                                    modifier = Modifier.size(24.dp)
                                                )
                                            }

                                            Spacer(modifier = Modifier.height(12.dp))

                                            // Label dengan background
                                            Box(
                                                modifier = Modifier
                                                    .background(
                                                        color = backgroundGreen,
                                                        shape = RoundedCornerShape(8.dp)
                                                    )
                                                    .padding(horizontal = 12.dp, vertical = 6.dp)
                                            ) {
                                                Text(
                                                    text = "Label: ${item.label}",
                                                    fontSize = 14.sp,
                                                    color = primaryGreen,
                                                    fontWeight = FontWeight.Medium
                                                )
                                            }

                                            Spacer(modifier = Modifier.height(12.dp))

                                            // Status setoran
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                modifier = Modifier
                                                    .background(
                                                        color = if (item.sudah_setor)
                                                            Color(0xFFE8F5E8) else Color(0xFFFFEBEE),
                                                        shape = RoundedCornerShape(8.dp)
                                                    )
                                                    .padding(horizontal = 12.dp, vertical = 8.dp)
                                                    .fillMaxWidth()
                                            ) {
                                                Icon(
                                                    imageVector = if (item.sudah_setor) Icons.Default.CheckCircle else Icons.Default.Close,
                                                    contentDescription = null,
                                                    tint = if (item.sudah_setor) secondaryGreen else Color(0xFFE57373),
                                                    modifier = Modifier.size(20.dp)
                                                )
                                                Spacer(modifier = Modifier.width(8.dp))
                                                Text(
                                                    text = if (item.sudah_setor) "Sudah Setor" else "Belum Setor",
                                                    fontSize = 14.sp,
                                                    color = if (item.sudah_setor) primaryGreen else Color(0xFFD32F2F),
                                                    fontWeight = FontWeight.SemiBold
                                                )
                                            }

                                            // Info setoran jika ada
                                            item.info_setoran?.let { info ->
                                                Spacer(modifier = Modifier.height(12.dp))

                                                Divider(
                                                    color = lightGreen,
                                                    thickness = 1.dp,
                                                    modifier = Modifier.padding(vertical = 8.dp)
                                                )

                                                Row(
                                                    modifier = Modifier.fillMaxWidth(),
                                                    horizontalArrangement = Arrangement.SpaceBetween
                                                ) {
                                                    Column(modifier = Modifier.weight(1f)) {
                                                        Text(
                                                            text = "Tanggal Setoran",
                                                            fontSize = 12.sp,
                                                            color = Color.Gray,
                                                            fontWeight = FontWeight.Medium
                                                        )
                                                        Text(
                                                            text = formatDate(info.tgl_setoran), // Menggunakan fungsi formatDate
                                                            fontSize = 14.sp,
                                                            color = primaryGreen,
                                                            fontWeight = FontWeight.SemiBold
                                                        )
                                                    }
                                                }

                                                Spacer(modifier = Modifier.height(8.dp))

                                                Row(
                                                    modifier = Modifier.fillMaxWidth(),
                                                    verticalAlignment = Alignment.CenterVertically
                                                ) {
                                                    Icon(
                                                        imageVector = Icons.Default.Person,
                                                        contentDescription = null,
                                                        tint = lightGreen,
                                                        modifier = Modifier.size(16.dp)
                                                    )
                                                    Spacer(modifier = Modifier.width(6.dp))
                                                    Column {
                                                        Text(
                                                            text = "Dosen Pengesah",
                                                            fontSize = 12.sp,
                                                            color = Color.Gray,
                                                            fontWeight = FontWeight.Medium
                                                        )
                                                        Text(
                                                            text = info.dosen_yang_mengesahkan.nama,
                                                            fontSize = 14.sp,
                                                            color = primaryGreen,
                                                            fontWeight = FontWeight.SemiBold
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                Card(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp),
                    colors = CardDefaults.cardColors(containerColor = pureWhite),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = null,
                            tint = Color(0xFFE57373),
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Gagal memuat data: ${setoranState?.message}",
                            color = Color(0xFFD32F2F),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}