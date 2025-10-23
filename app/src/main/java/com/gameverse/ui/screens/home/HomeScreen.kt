package com.gameverse.ui.screens.home

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.gameverse.ui.components.AnimatedImage
import com.gameverse.ui.components.FullScreenLoader
import com.gameverse.ui.components.LogoImage
import com.gameverse.ui.components.rememberNeonFlicker
import com.gameverse.viewmodel.MainViewModel

@Composable
fun HomeScreen(mainViewModel: MainViewModel = viewModel()) {
    val uiState by mainViewModel.uiState.collectAsState()
    val context = LocalContext.current

    // Definimos el ImageLoader una sola vez para reutilizarlo
    val imageLoader = remember {
        ImageLoader.Builder(context)
            .components {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
                    add(ImageDecoderDecoder.Factory())
                } else {
                    add(GifDecoder.Factory())
                }
            }
            .build()
    }

    if (uiState.isLoading) {
        FullScreenLoader()
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // 1. Logo animado
            LogoImage(modifier = Modifier.height(150.dp))

            // 2. GIF Ancho ("Voltereta")
            AnimatedImage(
                model = "https://i.postimg.cc/52KTJLWz/todos-voltereta.gif",
                contentDescription = "Animación Voltereta",
                imageLoader = imageLoader,
                modifier = Modifier
                    .fillMaxWidth(0.8f) // Achicado al 80%
                    .aspectRatio(16f / 2.5f)
            )

            // Tarjeta de bienvenida con borde neón
            val animatedBorderColor = rememberNeonFlicker()
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 2.dp,
                        color = animatedBorderColor,
                        shape = RoundedCornerShape(12.dp)
                    ),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    // Hacemos la tarjeta semitransparente para que se vea el fondo
                    containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f)
                )
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Bienvenido a LVL-UP Gamer",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Tu destino número uno para hardware de alto rendimiento, noticias de última hora y la comunidad de gaming más apasionada. Aquí es donde tu juego sube de nivel.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color(0xFFFEFCF9),
                        textAlign = TextAlign.Center
                    )
                }
            }

            // GIF Mediano ("Nube")
            AnimatedImage(
                model = "https://i.postimg.cc/nhSgxBfs/nube-semaforo.gif",
                contentDescription = "Animación Nube",
                imageLoader = imageLoader,
                modifier = Modifier
                    .fillMaxWidth(0.25f) // Achicado al 25%
                    .aspectRatio(1f)
            )


            // Tarjeta 2: "¿Qué encontraré aquí?"
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 2.dp,
                        color = animatedBorderColor,
                        shape = RoundedCornerShape(12.dp)
                    ),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f)
                )
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "¿Qué encontraré aquí?",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "En nuestra aplicación encontrarás un sin fin de productos Gamers, desde nivel básico hasta profesional, para desbloquear tu potencial al nivel 99!",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color(0xFFFEFCF9),
                        textAlign = TextAlign.Center
                    )
                }
            }

            // GIF Mediano ("Mario")
            AnimatedImage(
                model = "https://i.postimg.cc/Bn7k3Hfj/mario.gif",
                contentDescription = "Animación Mario",
                imageLoader = imageLoader,
                modifier = Modifier
                    .fillMaxWidth(0.25f) // Achicado al 25%
                    .aspectRatio(1f)
            )

            // Tarjeta 3: "¿Próximas mejoras?"
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 2.dp,
                        color = animatedBorderColor,
                        shape = RoundedCornerShape(12.dp)
                    ),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f)
                )
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "¿Próximas mejoras?",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Nuestros desarrolladores tienen planificado funciones como: Despacho a domicilio, seguimiento de tu compra, contacto con el personal de LVL-UP",
                        style = MaterialTheme. typography.bodyLarge,
                        color = Color(0xFFFEFCF9),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

