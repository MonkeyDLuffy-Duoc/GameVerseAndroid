package com.gameverse.ui.components

import com.gameverse.R
import android.os.Build
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import java.text.NumberFormat
import java.util.Locale
import kotlinx.coroutines.delay

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
            .size(200.dp) // Tamaño agrandado
            .clip(RoundedCornerShape(16.dp))
    )
}

@Composable
fun AnimatedImage(
    model: Any,
    imageLoader: ImageLoader,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop
) {
    AsyncImage(
        model = model,
        imageLoader = imageLoader,
        contentDescription = contentDescription,
        modifier = modifier.clip(RoundedCornerShape(12.dp)),
        contentScale = contentScale
    )
}

@Composable
fun rememberNeonFlicker(): Color {
    val baseColor = MaterialTheme.colorScheme.primary
    var targetAlpha by remember { mutableStateOf(1f) }

    LaunchedEffect(Unit) {
        while (true) {
            targetAlpha = 0.5f + (Math.random() * 0.5f).toFloat()
            delay((100 + Math.random() * 150).toLong())
        }
    }

    val animatedAlpha by animateFloatAsState(
        targetValue = targetAlpha,
        animationSpec = tween(durationMillis = 100, easing = LinearEasing),
        label = "Neon Flicker Alpha"
    )

    return baseColor.copy(alpha = animatedAlpha)
}


@Composable
fun NeonTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    val animatedBorderColor = rememberNeonFlicker()

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        shape = RoundedCornerShape(8.dp),
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = animatedBorderColor,
            unfocusedBorderColor = animatedBorderColor.copy(alpha = 0.5f),
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
    val animatedBorderColor = rememberNeonFlicker()

    Button(
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(8.dp),
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
            .border(1.dp, animatedBorderColor, RoundedCornerShape(8.dp)),
        colors = ButtonDefaults.buttonColors(
            containerColor = animatedBorderColor.copy(alpha = 0.1f),
            contentColor = animatedBorderColor // Color del texto
        )
    ) {
        Text(text.uppercase())
    }
}

@Composable
fun ProductCard(product: Product, onAddToCart: (Product) -> Unit) {
    // 1. Prepara el formateador de CLP
    val clpFormatter = remember { NumberFormat.getCurrencyInstance(Locale("es", "CL")) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        // 2. ¡CORRECCIÓN AQUÍ!
        // Hacemos la tarjeta semitransparente para que se vea el fondo global
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f)
        )
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
                Text(product.name, style = MaterialTheme.typography.titleLarge, color = Color(0xFFFEFCF9))
                Spacer(Modifier.height(4.dp))
                Text(
                    text = product.details,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    color = Color(0xFFFEFCF9),
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.height(16.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        // 3. Aplicamos el formato CLP
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
        shape = RoundedCornerShape(12.dp),
        // Hacemos la tarjeta de noticias semitransparente también
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f)
        )
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
                Text(newsItem.title, style = MaterialTheme.typography.titleMedium, maxLines = 2, overflow = TextOverflow.Ellipsis, color = Color(0xFFFEFCF9))
                Spacer(Modifier.height(4.dp))
                Text(newsItem.summary, style = MaterialTheme.typography.bodySmall, maxLines = 3, overflow = TextOverflow.Ellipsis, color = Color(0xFFFEFCF9))
            }
        }
    }
}

// Dentro de tu archivo CommonUI.kt

@Composable
fun FullScreenLoader() {
    // 1. Necesitamos el contexto y el ImageLoader para animaciones
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

    Box(
        modifier = Modifier
            .fillMaxSize()
            // Mantenemos el fondo oscuro semitransparente
            .background(MaterialTheme.colorScheme.background.copy(alpha = 0.8f)),
        contentAlignment = Alignment.Center
    ) {
        // 2. Reemplazamos CircularProgressIndicator por AsyncImage
        AsyncImage(
            // 3. Apunta a tu nuevo archivo en la carpeta drawable
            model = R.drawable.pikachu, // <-- Cambia esto por el nombre de tu archivo
            imageLoader = imageLoader,
            contentDescription = "Cargando...",
            modifier = Modifier.size(220.dp) // Ajusta el tamaño de tu GIF/WEBP
        )
    }
}

