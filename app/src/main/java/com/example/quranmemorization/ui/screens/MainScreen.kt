package com.example.quranmemorization.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.quranmemorization.TokenManager
import com.example.quranmemorization.ui.viewmodel.MainViewModel

// Modern Color Palette
private val PrimaryGreen = Color(0xFF2E7D32)
private val SecondaryGreen = Color(0xFF66BB6A)
private val LightGreen = Color(0xFFE8F5E8)
private val BackgroundColor = Color(0xFFFAFAFA)
private val SurfaceColor = Color.White
private val TextPrimary = Color(0xFF1A1A1A)
private val TextSecondary = Color(0xFF666666)

data class MenuItem(
    val title: String,
    val icon: ImageVector,
    val route: String,
    val color: Color = PrimaryGreen
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    username: String,
    onLogout: () -> Unit,
    navController: NavController,
    viewModel: MainViewModel
) {
    val setoranState by viewModel.setoranResponse.collectAsState()
    val context = LocalContext.current
    val tokenManager = remember { TokenManager(context) }

    Log.d("MainScreen", "username: $username, setoranState: $setoranState")

    LaunchedEffect(Unit) {
        viewModel.fetchSetoranData(context)
    }

    val menuItems = listOf(
        MenuItem("KP", Icons.Outlined.MenuBook, "detail_setoran/KP"),
        MenuItem("SEM KP", Icons.Outlined.School, "detail_setoran/SEMKP"),
        MenuItem("DAFTAR TA", Icons.Outlined.AssignmentInd, "detail_setoran/DAFTAR_TA"),
        MenuItem("SEMPRO", Icons.Outlined.PresentToAll, "detail_setoran/SEMPRO"),
        MenuItem("SIDANG TA", Icons.Outlined.Gavel, "detail_setoran/SIDANG_TA")
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Quran Memorization",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White // Ubah warna text menjadi putih
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = PrimaryGreen // Ubah background menjadi hijau
                ),
                actions = {
                    IconButton(
                        onClick = {
                            tokenManager.clearTokens()
                            onLogout()
                        },
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.2f)) // Background putih transparan
                    ) {
                        Icon(
                            imageVector = Icons.Default.Logout,
                            contentDescription = "Keluar",
                            tint = Color.White // Icon putih
                        )
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = PrimaryGreen, // Ubah background menjadi hijau
                tonalElevation = 8.dp
            ) {
                NavigationBarItem(
                    selected = navController.currentDestination?.route?.startsWith("home") == true,
                    onClick = { navController.navigate("home/$username") },
                    icon = {
                        Icon(
                            if (navController.currentDestination?.route?.startsWith("home") == true)
                                Icons.Filled.Home else Icons.Outlined.Home,
                            contentDescription = "Beranda"
                        )
                    },
                    label = { Text("Beranda", fontSize = 12.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White, // Icon terpilih putih
                        selectedTextColor = Color.White, // Text terpilih putih
                        unselectedIconColor = Color.White.copy(alpha = 0.7f), // Icon tidak terpilih putih transparan
                        unselectedTextColor = Color.White.copy(alpha = 0.7f), // Text tidak terpilih putih transparan
                        indicatorColor = Color.White.copy(alpha = 0.2f) // Indikator putih transparan
                    )
                )
                NavigationBarItem(
                    selected = navController.currentDestination?.route?.startsWith("profile") == true,
                    onClick = { navController.navigate("profile/$username") },
                    icon = {
                        Icon(
                            if (navController.currentDestination?.route?.startsWith("profile") == true)
                                Icons.Filled.Person else Icons.Outlined.Person,
                            contentDescription = "Profil"
                        )
                    },
                    label = { Text("Profil", fontSize = 12.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White, // Icon terpilih putih
                        selectedTextColor = Color.White, // Text terpilih putih
                        unselectedIconColor = Color.White.copy(alpha = 0.7f), // Icon tidak terpilih putih transparan
                        unselectedTextColor = Color.White.copy(alpha = 0.7f), // Text tidak terpilih putih transparan
                        indicatorColor = Color.White.copy(alpha = 0.2f) // Indikator putih transparan
                    )
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundColor)
                .padding(paddingValues)
        ) {
            when {
                setoranState == null -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            CircularProgressIndicator(
                                color = PrimaryGreen,
                                strokeWidth = 3.dp
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                "Memuat data...",
                                color = TextSecondary,
                                fontSize = 14.sp
                            )
                        }
                    }
                }
                setoranState?.response == true -> {
                    val data = setoranState!!.data

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        // Welcome Section
                        WelcomeCard(username)

                        // Progress Card
                        ProgressCard(data.setoran.info_dasar.persentase_progres_setor ?: 0)

                        // Menu Grid
                        Text(
                            "Menu Setoran",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = TextPrimary
                        )

                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(menuItems) { item ->
                                MenuCard(
                                    menuItem = item,
                                    onClick = { navController.navigate(item.route) }
                                )
                            }
                        }
                    }
                }
                else -> {
                    ErrorMessage(setoranState?.message ?: "Kesalahan tidak diketahui")
                }
            }
        }
    }
}

@Composable
fun WelcomeCard(username: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = SurfaceColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(LightGreen),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.Person,
                    contentDescription = null,
                    tint = PrimaryGreen,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    "Assalamu'alaikum",
                    fontSize = 14.sp,
                    color = TextSecondary
                )
                Text(
                    username,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextPrimary
                )
                Text(
                    "Semoga hafalan berkah",
                    fontSize = 12.sp,
                    color = TextSecondary,
                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                )
            }
        }
    }
}

@Composable
fun ProgressCard(progress: Number) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = SurfaceColor
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        "Progres Hafalan",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = TextPrimary
                    )
                    Text(
                        "Terus semangat!",
                        fontSize = 12.sp,
                        color = TextSecondary
                    )
                }

                Box(
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        progress = progress.toFloat() / 100f,
                        modifier = Modifier.size(56.dp),
                        color = PrimaryGreen,
                        trackColor = LightGreen,
                        strokeWidth = 6.dp,
                        strokeCap = androidx.compose.ui.graphics.StrokeCap.Round
                    )
                    Text(
                        "$progress%",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryGreen
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            LinearProgressIndicator(
                progress = progress.toFloat() / 100f,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp)),
                color = PrimaryGreen,
                trackColor = LightGreen
            )
        }
    }
}

@Composable
fun MenuCard(
    menuItem: MenuItem,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1.2f),
        colors = CardDefaults.cardColors(containerColor = SurfaceColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(LightGreen),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = menuItem.icon,
                    contentDescription = menuItem.title,
                    tint = PrimaryGreen,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = menuItem.title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = TextPrimary,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun ErrorMessage(message: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier.padding(32.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceColor),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    Icons.Default.ErrorOutline,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.error,
                    modifier = Modifier.size(48.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    "Oops!",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextPrimary
                )
                Text(
                    message,
                    fontSize = 14.sp,
                    color = TextSecondary,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}