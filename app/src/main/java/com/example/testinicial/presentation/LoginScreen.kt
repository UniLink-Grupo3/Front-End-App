package com.example.testinicial.presentation

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.testinicial.R
import com.example.testinicial.Routes
import com.example.testinicial.data.remote.RetrofitInstance
import com.example.testinicial.data.remote.model.LoginRequest
import com.example.testinicial.presentation.components.AppScreen
import com.example.testinicial.ui.theme.TestInicialTheme
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(navController: NavHostController) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val loginError = remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current
    val primaryBlue = Color(0xFF3A5A98)
    val secondaryOrange = Color(0xFFF57C00)
    val backgroundColor = Color(0xFFF3F4F6)
    val errorRed = Color(0xFFD32F2F)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 40.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(120.dp)
                    .padding(bottom = 12.dp)
            )

            Text(
                text = "RideUp",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.ExtraBold,
                    color = primaryBlue
                )
            )

            Text(
                text = "Tu viaje empieza aquí",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color.Gray
                ),
                modifier = Modifier.padding(bottom = 32.dp)
            )

            OutlinedTextField(
                value = email.value,
                onValueChange = { email.value = it },
                label = { Text("Correo electrónico") },
                leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = primaryBlue,
                    unfocusedBorderColor = primaryBlue.copy(alpha = 0.5f),
                    focusedLabelColor = primaryBlue,
                    cursorColor = primaryBlue
                )
            )

            OutlinedTextField(
                value = password.value,
                onValueChange = { password.value = it },
                label = { Text("Contraseña") },
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = null
                        )
                    }
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = primaryBlue,
                    unfocusedBorderColor = primaryBlue.copy(alpha = 0.5f),
                    focusedLabelColor = primaryBlue,
                    cursorColor = primaryBlue
                )
            )

            loginError.value?.let {
                Text(
                    text = it,
                    color = errorRed,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .align(Alignment.Start)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    coroutineScope.launch {
                        try {
                            val response = RetrofitInstance.authApi.login(
                                LoginRequest(email.value.trim(), password.value)
                            )

                            if (response.isSuccessful) {
                                Toast.makeText(context, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()
                                navController.navigate(Routes.Home.route) {
                                    popUpTo(Routes.Login.route) { inclusive = true }
                                }
                            } else {
                                loginError.value = "Credenciales inválidas"
                            }
                        } catch (e: Exception) {
                            loginError.value = "Error al conectar con el servidor"
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = primaryBlue,
                    contentColor = Color.White
                )
            ) {
                Text("Iniciar sesión")
            }

            Spacer(modifier = Modifier.height(24.dp))

            TextButton(onClick = {
                navController.navigate(AppScreen.Register.route)
            }) {
                Text(
                    text = "¿No tienes cuenta? ",
                    style = MaterialTheme.typography.bodyMedium.copy(color = Color.DarkGray)
                )
                Text(
                    text = "Regístrate",
                    style = MaterialTheme.typography.bodyMedium.copy(color = secondaryOrange, fontWeight = FontWeight.Bold)
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewLoginScreen() {
    val navController = rememberNavController()
    TestInicialTheme {
        LoginScreen(navController = navController)
    }
}
