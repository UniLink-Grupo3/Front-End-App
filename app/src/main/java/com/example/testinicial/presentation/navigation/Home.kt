package com.example.testinicial.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.testinicial.Routes
import com.example.testinicial.presentation.LoginScreen
import com.example.testinicial.presentation.RegisterScreen

@Preview
@Composable
fun Home() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.Login.route) {
        composable(Routes.Login.route) {
            LoginScreen(navController = navController)
        }
        composable(Routes.Register.route) {
            RegisterScreen(navController = navController)
        }
    }
}
