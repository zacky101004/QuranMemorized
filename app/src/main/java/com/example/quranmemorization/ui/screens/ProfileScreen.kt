package com.example.quranmemorization.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.quranmemorization.ui.viewmodel.MainViewModel

@Composable
fun ProfileScreen(
    username: String,
    viewModel: MainViewModel,
    navController: NavController
) {
    val setoranState by viewModel.setoranResponse.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        if (setoranState == null) {
            Log.d("ProfileScreen", "Fetching data for username: $username")
            viewModel.fetchSetoranData(context)
        }
    }

    Log.d("ProfileScreen", "username: $username, setoranState: $setoranState")

    val initials = when {
        setoranState?.response == true -> {
            val name = setoranState?.data?.info?.nama ?: ""
            getInitials(name)
        }
        else -> "--"
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFFE8F5E9), Color(0xFFF1F8E9))
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 24.dp)
        ) {
            // Header with back button and title
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier
                        .size(48.dp)
                        .background(Color.White, CircleShape)
                        .shadow(4.dp, CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color(0xFF388E3C)
                    )
                }
                Text(
                    text = "Profile",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF1B5E20),
                    letterSpacing = 0.5.sp
                )
                Spacer(modifier = Modifier.width(48.dp)) // Placeholder for symmetry
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Profile Initials Circle
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.linearGradient(
                            colors = listOf(Color(0xFF4CAF50), Color(0xFF81C784)),
                            start = androidx.compose.ui.geometry.Offset(0f, 0f),
                            end = androidx.compose.ui.geometry.Offset(120f, 120f)
                        )
                    )
                    .align(Alignment.CenterHorizontally)
                    .shadow(8.dp, CircleShape)
            ) {
                Text(
                    text = initials,
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Black,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    letterSpacing = 1.sp
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Profile Info Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .shadow(12.dp, RoundedCornerShape(24.dp)),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {
                Column(
                    modifier = Modifier
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color(0xFFFFFFFF),
                                    Color(0xFFE8F5E9)
                                )
                            )
                        )
                        .padding(20.dp)
                ) {
                    when {
                        setoranState == null -> {
                            CircularProgressIndicator(
                                modifier = Modifier.align(Alignment.CenterHorizontally),
                                color = Color(0xFF388E3C)
                            )
                        }
                        setoranState?.response == true -> {
                            val data = setoranState!!.data
                            ProfileItem(
                                icon = Icons.Default.Person,
                                label = "Nama",
                                value = data.info.nama ?: "N/A"
                            )
                            ProfileItem(
                                icon = Icons.Default.Add,
                                label = "NIM",
                                value = data.info.nim ?: "N/A"
                            )
                            ProfileItem(
                                icon = Icons.Default.Email,
                                label = "Email",
                                value = data.info.email ?: "N/A"
                            )
                            ProfileItem(
                                icon = Icons.Default.DateRange,
                                label = "Semester",
                                value = data.info.semester?.toString() ?: "N/A"
                            )
                            ProfileItem(
                                icon = Icons.Default.AccountBox,
                                label = "Angkatan",
                                value = data.info.angkatan ?: "N/A"
                            )
                            ProfileItem(
                                icon = Icons.Default.Face,
                                label = "Dosen Pembimbing",
                                value = data.info.dosen_pa?.nama ?: "N/A"
                            )
                        }
                        else -> {
                            Text(
                                text = "Failed to load data: ${setoranState?.message ?: "Unknown error"}",
                                color = Color(0xFFD32F2F),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.align(Alignment.CenterHorizontally),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Color(0xFF4CAF50).copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color(0xFF388E3C),
                modifier = Modifier.size(24.dp)
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(
                text = label,
                fontSize = 14.sp,
                color = Color(0xFF4CAF50),
                fontWeight = FontWeight.Medium,
                letterSpacing = 0.5.sp
            )
            Text(
                text = value,
                fontSize = 18.sp,
                color = Color(0xFF1B5E20),
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

// Helper function to get initials from full name
fun getInitials(fullName: String): String {
    val parts = fullName.trim().split(" ")
    return when {
        parts.size >= 2 -> "${parts[0].firstOrNull() ?: ""}${parts[1].firstOrNull() ?: ""}".uppercase()
        parts.isNotEmpty() -> parts[0].take(2).uppercase()
        else -> "--"
    }
}