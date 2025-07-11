package com.example.testinicial.presentation.support

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.HeadsetMic
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

val BlueMetallic = Color(0xFF3A5A98)
val ChatGray = Color(0xFFE0E0E0)
val BackgroundGray = Color(0xFFF4F6F9)
val OrangeAccent = Color(0xFFF57C00)

@Composable
fun SupportScreen() {
    var userMessage by remember { mutableStateOf("") }
    var messages by remember {
        mutableStateOf(
            listOf(
                ChatMessage("¡Hola! 👋 ¿En qué podemos ayudarte hoy?", isUser = false)
            )
        )
    }
    var showRating by remember { mutableStateOf(false) }
    var rating by remember { mutableStateOf(0) }
    var isTyping by remember { mutableStateOf(false) }
    var newUserMessage by remember { mutableStateOf<String?>(null) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            imageUri = uri
            messages = messages + ChatMessage("📎 Imagen adjunta: ${uri.lastPathSegment ?: "archivo.jpg"}", isUser = true)
        }
    }

    LaunchedEffect(newUserMessage) {
        newUserMessage?.let {
            isTyping = true
            delay(1000)
            messages = messages + ChatMessage(
                "RideUp te permite compartir viajes con otros estudiantes de tu universidad para ahorrar tiempo y dinero.",
                isUser = false
            )
            delay(2000)
            messages = messages + ChatMessage(
                "Espero que mi respuesta te haya ayudado. ¿Deseas calificar mi atención?",
                isUser = false
            )
            isTyping = false
            showRating = true
            newUserMessage = null
        }
    }

    LaunchedEffect(imageUri) {
        imageUri?.let {
            isTyping = true
            delay(1500)
            messages = messages + ChatMessage(
                "Hemos recibido la imagen adjunta. Lamentamos mucho el inconveniente que experimentas. Según el análisis, parece estar relacionado con una versión desactualizada del sistema operativo de tu teléfono.",
                isUser = false
            )
            delay(1500)
            messages = messages + ChatMessage(
                "Te recomendamos actualizar tu sistema operativo a la última versión disponible para asegurar el correcto funcionamiento de la app. Si el problema persiste, no dudes en contactarnos nuevamente.",
                isUser = false
            )
            isTyping = false
            showRating = true
            imageUri = null
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundGray)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(BlueMetallic)
                .padding(vertical = 20.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.HeadsetMic,
                contentDescription = "Soporte",
                tint = Color.White,
                modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                "Servicio al Cliente",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            messages.forEach { msg ->
                ChatBubble(msg.text, isUser = msg.isUser)
                Spacer(modifier = Modifier.height(12.dp))
            }

            if (isTyping) {
                Text(
                    "Escribiendo...",
                    style = MaterialTheme.typography.bodyMedium.copy(color = BlueMetallic)
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            if (showRating) {
                Text(
                    "Califica mi atención:",
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    color = BlueMetallic,
                    modifier = Modifier.padding(top = 12.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row {
                    (1..5).forEach { star ->
                        IconButton(onClick = { rating = star }) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = "Star",
                                tint = if (star <= rating) OrangeAccent else ChatGray
                            )
                        }
                    }
                }
                if (rating > 0) {
                    Text(
                        "¡Gracias por tu calificación!",
                        style = MaterialTheme.typography.bodyMedium.copy(color = BlueMetallic)
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = userMessage,
                onValueChange = { userMessage = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Escribe tu mensaje...") },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = BlueMetallic,
                    unfocusedBorderColor = ChatGray
                )
            )
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(
                onClick = {
                    if (userMessage.isNotBlank()) {
                        val input = userMessage.trim()
                        userMessage = ""
                        messages = messages + ChatMessage(input, isUser = true)
                        newUserMessage = input
                    }
                },
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(BlueMetallic)
            ) {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = "Enviar",
                    tint = Color.White
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(
                onClick = {
                    pickImageLauncher.launch("image/*")
                },
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(OrangeAccent)
            ) {
                Icon(
                    imageVector = Icons.Default.AttachFile,
                    contentDescription = "Adjuntar archivo",
                    tint = Color.White
                )
            }
        }
    }
}

data class ChatMessage(val text: String, val isUser: Boolean)

@Composable
fun ChatBubble(text: String, isUser: Boolean) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = if (isUser) Alignment.CenterEnd else Alignment.CenterStart
    ) {
        Surface(
            color = if (isUser) BlueMetallic else ChatGray,
            shape = MaterialTheme.shapes.medium,
            tonalElevation = 2.dp,
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            Text(
                text = text,
                modifier = Modifier.padding(12.dp),
                color = if (isUser) Color.White else Color.Black
            )
        }
    }
}
