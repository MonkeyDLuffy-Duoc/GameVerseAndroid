package com.gameverse.ui.screens.cart

import java.text.NumberFormat
import java.util.Locale
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.gameverse.data.model.CartProduct
import com.gameverse.ui.components.NeonButton
import com.gameverse.viewmodel.CartViewModel

@Composable
fun CartScreen(cartViewModel: CartViewModel = viewModel()) {
    val clpFormatter = remember { NumberFormat.getCurrencyInstance(Locale("es", "CL")) }
    val uiState by cartViewModel.uiState.collectAsState()
    val context = LocalContext.current

    // Este 'LaunchedEffect' observa cambios en 'paymentSuccess'.
    // Se ejecuta solo una vez cuando el valor cambia a 'true'.
    LaunchedEffect(uiState.paymentSuccess) {
        if (uiState.paymentSuccess) {
            Toast.makeText(context, "¡Gracias por tu compra!", Toast.LENGTH_LONG).show()
            cartViewModel.resetPaymentStatus() // Limpia el estado para evitar que el toast se repita.
        }
    }

    // Comprueba si la lista correcta ('cartItems') está vacía.
    if (uiState.cartItems.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "Tu carrito está vacío",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 20.sp
            )
        }
    } else {
        Column(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // **CORRECCIÓN 1:** Usamos 'uiState.cartItems' y pasamos cada 'cartProduct' al CartItem.
                items(uiState.cartItems, key = { it.id }) { cartProduct ->
                    CartItem(
                        product = cartProduct,
                        onRemove = { cartViewModel.removeFromCart(cartProduct.id) }
                    )
                }
            }

            // Sección del total y pago.
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "Total:",
                        fontSize = 22.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        clpFormatter.format(uiState.total),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                NeonButton(
                    text = "Simular Pago",
                    onClick = {
                        cartViewModel.checkout()
                    } // La lógica del Toast ahora está en el LaunchedEffect.
                )
            }
        }
    }
}

@Composable
private fun CartItem(product: CartProduct, onRemove: () -> Unit) {
    val clpFormatter = remember { NumberFormat.getCurrencyInstance(Locale("es", "CL")) }
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            AsyncImage(
                model = product.imageUrl,
                contentDescription = product.name,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp)
            ) {
                Text(product.name, fontWeight = FontWeight.Bold)
                Text(
                    clpFormatter.format(product.price),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 14.sp
                )
            }
            IconButton(onClick = onRemove) {
                Icon(
                    painter = painterResource(id = android.R.drawable.ic_menu_delete),
                    contentDescription = "Eliminar del carrito",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}



