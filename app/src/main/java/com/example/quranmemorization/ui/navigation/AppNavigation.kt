package com.example.quranmemorization.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.quranmemorization.ui.screens.DetailSetoranScreen
import com.example.quranmemorization.ui.screens.LoginScreen
import com.example.quranmemorization.ui.screens.MainScreen
import com.example.quranmemorization.ui.screens.ProfileScreen
import com.example.quranmemorization.ui.viewmodel.MainViewModel

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            LoginScreen(onLoginSuccess = { username ->
                navController.navigate("home/$username") {
                    popUpTo("login") { inclusive = true }
                }
            })
        }
        composable(
            route = "home/{username}",
            arguments = listOf(navArgument("username") { type = NavType.StringType })
        ) { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: ""
            val viewModel: MainViewModel = viewModel()
            MainScreen(
                username = username,
                onLogout = {
                    navController.navigate("login") {
                        popUpTo("home/$username") { inclusive = true }
                    }
                },
                navController = navController,
                viewModel = viewModel
            )
        }
        composable(
            route = "profile/{username}",
            arguments = listOf(navArgument("username") { type = NavType.StringType })
        ) { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: ""
            val viewModel: MainViewModel = viewModel()
            ProfileScreen(
                username = username,
                viewModel = viewModel,
                navController = navController // Tambahkan NavController
            )
        }
        composable(
            route = "detail_setoran/{category}",
            arguments = listOf(navArgument("category") { type = NavType.StringType })
        ) { backStackEntry ->
            val category = backStackEntry.arguments?.getString("category") ?: ""
            val viewModel: MainViewModel = viewModel()
            DetailSetoranScreen(
                category = category,
                navController = navController,
                viewModel = viewModel
            )
        }
    }
}