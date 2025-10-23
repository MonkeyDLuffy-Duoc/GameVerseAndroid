package com.gameverse.ui.components

import java.text.NumberFormat
import java.util.Locale
import com.gameverse.R
import android.os.Build
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.gameverse.data.model.NewsItem
import com.gameverse.data.model.Product

/**
 * ¡NUEVA FUNCIÓN!
 * Crea una animación de color infinita que simula el parpadeo de un neón.
 * Devuelve el color animado que se debe usar en los bordes.
 */
@Composable
private fun rememberNeonFlicker(): Color {
    val infiniteTransition = rememberInfiniteTransition(label = "neon_flicker_transition")
    val baseColor = MaterialTheme.colorScheme.primary

    val animatedColor by infiniteTransition.animateColor(
        initialValue = baseColor.copy(alpha = 1.0f),
        targetValue = baseColor.copy(alpha = 0.5f), // El target no importa con keyframes
        animationSpec = infiniteRepeatable(
            // Definimos "fotogramas clave" para un parpadeo irregular
            animation = keyframes {
                durationMillis = 2500
                baseColor.copy(alpha = 1.0f) at 0
                baseColor.copy(alpha = 1.0f) at 2000
                baseColor.copy(alpha = 0.3f) at 2100 // Parpadeo rápido
                baseColor.copy(alpha = 1.0f) at 2200
                baseColor.copy(alpha = 0.7f) at 2300 // Parpadeo suave
                baseColor.copy(alpha = 1.0f) at 2500
            },
            repeatMode = RepeatMode.Restart // Reinicia la animación
        ),
        label = "neon_flicker_color"
    )
    return animatedColor
}


/**
 * Muestra el logo de la app cargándolo desde tus recursos 'drawable'.
 */
@Composable
fun LogoImage(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val imageLoader = remember {
        ImageLoader.Builder(context)
            .components {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    add(ImageDecoderDecoder.Factory())
                } else {
                    add(GifDecoder.Factory())
                }
            }
            .build()
    }

    AsyncImage(
        model = R.drawable.gameverse_logo,
        imageLoader = imageLoader,
        contentDescription = "Logo de Gameverse",
        modifier = modifier
            .size(200.dp)
            .clip(RoundedCornerShape(16.dp))
    )
}

@Composable
fun NeonTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    // ¡CAMBIO! Obtenemos el color animado
    val animatedBorderColor = rememberNeonFlicker()

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        shape = RoundedCornerShape(8.dp),
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            // ¡CAMBIO! Usamos el color animado
            focusedBorderColor = animatedBorderColor,
            unfocusedBorderColor = animatedBorderColor.copy(alpha = 0.5f), // Más tenue si no está enfocado
            focusedLabelColor = animatedBorderColor,
            cursorColor = animatedBorderColor
        ),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        visualTransformation = if (keyboardType == KeyboardType.Password) PasswordVisualTransformation() else VisualTransformation.None,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun NeonButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    // ¡CAMBIO! Obtenemos el color animado
    val animatedBorderColor = rememberNeonFlicker()

    Button(
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(8.dp),
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
            // ¡CAMBIO! Usamos el color animado para el borde
            .border(1.dp, animatedBorderColor, RoundedCornerShape(8.dp)),
        colors = ButtonDefaults.buttonColors(
            // ¡CAMBIO! Usamos el color animado para el fondo (con poca opacidad)
            containerColor = animatedBorderColor.copy(alpha = 0.1f),
            // ¡MODIFICACIÓN! Añadimos esta línea para que el texto también se anime
            contentColor = animatedBorderColor
        )
    ) {
        Text(text.uppercase())
    }
}

@Composable
fun ProductCard(product: Product, onAddToCart: (Product) -> Unit) {
    val clpFormatter = remember { NumberFormat.getCurrencyInstance(Locale("es", "CL")) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            AsyncImage(
                model = product.imageUrl,
                contentDescription = product.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)),
                contentScale = ContentScale.Crop
            )
            Column(Modifier.padding(16.dp)) {
                Text(product.name, style = MaterialTheme.typography.titleLarge)
                Spacer(Modifier.height(4.dp))
                Text(
                    text = product.details,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.height(16.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = clpFormatter.format(product.price),
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Button(onClick = { onAddToCart(product) }) {
                        Text("Comprar")
                    }
                }
            }
        }
    }
}

@Composable
fun NewsCard(newsItem: NewsItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(modifier = Modifier.height(IntrinsicSize.Min)) {
            AsyncImage(
                model = newsItem.imageUrl,
                contentDescription = newsItem.title,
                modifier = Modifier
                    .size(120.dp)
                    .clip(RoundedCornerShape(topStart = 12.dp, bottomStart = 12.dp)),
                contentScale = ContentScale.Crop
            )
            Column(Modifier.padding(16.dp)) {
                Text(newsItem.title, style = MaterialTheme.typography.titleMedium, maxLines = 2, overflow = TextOverflow.Ellipsis)
                Spacer(Modifier.height(4.dp))
                Text(newsItem.summary, style = MaterialTheme.typography.bodySmall, maxLines = 3, overflow = TextOverflow.Ellipsis)
            }
        }
    }
}


@Composable
fun FullScreenLoader() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background.copy(alpha = 0.8f)),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.primary,
            strokeWidth = 4.dp
        )
    }
}

