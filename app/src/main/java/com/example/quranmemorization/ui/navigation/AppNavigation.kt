package com.example.quranmemorization.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.quranmemorization.ui.screens.LoginScreen
import com.example.quranmemorization.ui.screens.MainScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            LoginScreen(onLoginSuccess = { username ->
                navController.navigate("main/$username") {
                    popUpTo("login") { inclusive = true }
                }
            })
        }
        composable(
            route = "main/{username}",
            arguments = listOf(navArgument("username") { type = NavType.StringType })
        ) { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: ""
            MainScreen(username = username, onLogout = {
                navController.navigate("login") {
                    popUpTo("main/$username") { inclusive = true }
                }
            })
        }
    }
}