package com.example.testinicial.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.testinicial.R
import com.example.testinicial.Routes
import com.example.testinicial.data.remote.api.AuthService
import com.example.testinicial.ui.theme.TestInicialTheme
import kotlinx.coroutines.launch
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.testinicial.data.remote.RetrofitInstance
import com.example.testinicial.data.remote.model.RegisterRequest

@Composable
fun RegisterScreen(navController: NavHostController) {
    val username = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val phone = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val registerError = remember { mutableStateOf<String?>(null) }

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
                text = "Crea tu cuenta para comenzar",
                style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray),
                modifier = Modifier.padding(bottom = 32.dp)
            )

            OutlinedTextField(
                value = username.value,
                onValueChange = { username.value = it },
                label = { Text("Nombre completo") },
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
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
                value = phone.value,
                onValueChange = { phone.value = it },
                label = { Text("Teléfono") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
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

            OutlinedTextField(
                value = password.value,
                onValueChange = { password.value = it },
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
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

            registerError.value?.let {
                Text(
                    text = it,
                    color = errorRed,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    coroutineScope.launch {
                        try {
                            val request = RegisterRequest(
                                nombre = username.value.trim(),
                                correo = email.value.trim(),
                                numero = phone.value.trim(),
                                contrasena = password.value.trim()
                            )
                            RetrofitInstance.authApi.register(request)
                            Toast.makeText(context, "Registrado exitosamente", Toast.LENGTH_SHORT).show()
                        } catch (e: Exception) {
                            Toast.makeText(context, "No se pudo registrar. Continuando...", Toast.LENGTH_SHORT).show()
                        }
                        navController.navigate(Routes.Login.route)
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
                Text("Registrarse", style = MaterialTheme.typography.labelLarge)
            }

            Spacer(modifier = Modifier.height(24.dp))

            TextButton(onClick = {
                navController.navigate(Routes.Login.route)
            }) {
                Text(
                    text = "¿Ya tienes una cuenta? ",
                    style = MaterialTheme.typography.bodyMedium.copy(color = Color.DarkGray)
                )
                Text(
                    text = "Inicia sesión",
                    style = MaterialTheme.typography.bodyMedium.copy(color = secondaryOrange, fontWeight = FontWeight.Bold)
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewRegisterScreen() {
    val navController = rememberNavController()
    TestInicialTheme {
        RegisterScreen(navController = navController)
    }
}
