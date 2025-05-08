package com.example.quranmemorization.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.example.quranmemorization.R
import com.example.quranmemorization.ui.theme.IslamicGold
import com.example.quranmemorization.ui.theme.IslamicGreen
import com.example.quranmemorization.ui.theme.IslamicWhite
import com.example.quranmemorization.ui.theme.QuranMemorizationTheme
import com.example.quranmemorization.ui.viewmodel.MainViewModel

@Composable
fun MainScreen(username: String, viewModel: MainViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val surahList by viewModel.surahList.observeAsState(emptyList())

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(IslamicWhite)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.welcome_message, username),
                style = MaterialTheme.typography.titleLarge,
                color = IslamicGreen,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Text(
                text = stringResource(R.string.surah_list_title),
                style = MaterialTheme.typography.titleMedium,
                color = IslamicGreen,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(surahList) { surah ->
                    SurahCard(surah)
                }
            }
        }
    }
}

@Composable
fun SurahCard(surah: String) {
    var visible by androidx.compose.runtime.remember { androidx.compose.runtime.mutableStateOf(false) }
    LaunchedEffect(Unit) {
        visible = true
    }

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(animationSpec = tween(500))
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp)),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = surah,
                    style = MaterialTheme.typography.bodyLarge,
                    color = IslamicGreen,
                    modifier = Modifier.weight(1f)
                )
                Surface(
                    modifier = Modifier.size(24.dp),
                    shape = RoundedCornerShape(50),
                    color = IslamicGold
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = "✓",
                            color = IslamicWhite,
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    QuranMemorizationTheme {
        MainScreen(username = "User")
    }
}