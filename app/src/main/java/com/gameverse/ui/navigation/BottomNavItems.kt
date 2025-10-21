package com.gameverse.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Store
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Define las pantallas accesibles desde la barra de navegación inferior.
 * Cada objeto contiene la información necesaria para el sistema de navegación y la UI.
 */
sealed class BottomNavItems(val route: String, val title: String, val icon: ImageVector) {
    data object Home : BottomNavItems("home", "Inicio", Icons.Default.Home)
    data object Products : BottomNavItems("products", "Productos", Icons.Default.Store)
    data object News : BottomNavItems("news", "Noticias", Icons.Default.Info)
    data object Profile : BottomNavItems("profile", "Perfil", Icons.Default.AccountCircle)

    // El carrito no es parte de la barra de navegación inferior,
    // pero lo definimos aquí como una ruta principal para mantener la consistencia.
    data object Cart : BottomNavItems("cart", "Carrito", Icons.Default.ShoppingCart)
}