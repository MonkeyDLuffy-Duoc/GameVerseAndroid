package com.gameverse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.gameverse.ui.navigation.AppNavigation
import com.gameverse.ui.theme.GameverseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Habilita que la app se dibuje de borde a borde de la pantalla
        enableEdgeToEdge()
        setContent {
            // 1. Aplica el tema personalizado que definiste en ui/theme/Theme.kt
            GameverseTheme {
                // Un Surface es un contenedor básico de Material Design.
                // Usamos el color de fondo del tema.
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // 2. Llama al Composable que contiene toda la lógica de navegación.
                    // Este es el punto de partida de tu UI.
                    AppNavigation()
                    //HOLAESTOESUNCAMBIOPARAGITHUB
                    //A LO MEJOR FUNCIONA CON MÚLTIPLES LÍNEAS?
                    //UWUW LOL
                }
            }
        }
    }
}