package com.example.testinicial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.testinicial.presentation.navigation.Navigation
import com.example.testinicial.ui.theme.TestInicialTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestInicialTheme {
                val navController = rememberNavController()
                Navigation(navController)
            }
        }
    }
}
