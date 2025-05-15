package com.example.testinicial.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.testinicial.R

@Composable
fun BackgroundContainer(
    screen: AppScreen,
    content: @Composable () -> Unit
) {
    val backgroundRes = when (screen) {
        AppScreen.Login -> R.drawable.background_login
        AppScreen.Register -> R.drawable.background_register
        AppScreen.Main -> R.drawable.background_main
        AppScreen.Profile -> TODO()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = backgroundRes),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        content()
    }
}
