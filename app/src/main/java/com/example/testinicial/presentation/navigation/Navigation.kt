package com.example.testinicial.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.testinicial.Routes
import com.example.testinicial.presentation.LoginScreen
import com.example.testinicial.presentation.RegisterScreen
import com.example.testinicial.presentation.home.HomeScreen
import com.example.testinicial.presentation.profile.ProfileScreen

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Routes.Login.route) {
        composable(Routes.Login.route) {
            LoginScreen(navController = navController)
        }
        composable(Routes.Register.route) {
            RegisterScreen(navController = navController)
        }
        composable(Routes.Home.route) {
            HomeScreen(onNavigateToProfile = {
                navController.navigate(Routes.Profile.route)
            })
        }
        composable(Routes.Profile.route) {
            ProfileScreen()
        }
    }
}
