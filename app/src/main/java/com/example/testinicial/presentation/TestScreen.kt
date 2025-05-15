package com.example.testinicial.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.testinicial.presentation.home.HomeScreen

@Composable
fun TestScreen(navController: NavHostController) {
    var navigateToHome by remember { mutableStateOf(false) }

    if (navigateToHome) {
        HomeScreen(onNavigateToProfile = {})
    } else {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Pantalla de pruebas")

            Button(
                onClick = {
                    navigateToHome = true
                },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text("Ir al Home Screen")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTestScreen() {
    val navController = rememberNavController()
    TestScreen(navController = navController)
}
