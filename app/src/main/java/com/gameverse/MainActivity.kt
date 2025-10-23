package com.gameverse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.gameverse.ui.navigation.AppNavigation
import com.gameverse.ui.theme.GameverseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GameverseTheme {
                // 1. Usamos un Box para apilar el fondo y el contenido
                Box(modifier = Modifier.fillMaxSize()) {
                    // 2. Imagen de Fondo (la misma que usaste en HomeScreen)
                    Image(
                        painter = painterResource(id = R.drawable.home_background),
                        contentDescription = "Imagen de fondo global",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop,
                        colorFilter = ColorFilter.tint(Color.Black.copy(alpha = 0.6f), blendMode = BlendMode.Darken)
                    )

                    // 3. El Surface AHORA DEBE SER TRANSPARENTE
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = Color.Transparent // <-- ¡CAMBIO CLAVE!
                    ) {
                        // 4. La navegación se dibuja encima del fondo
                        AppNavigation()
                    }
                }
            }
        }
    }
}

