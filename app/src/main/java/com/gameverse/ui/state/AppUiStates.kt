package com.gameverse.ui.state

import com.gameverse.data.model.CartProduct
import com.gameverse.data.model.NewsItem
import com.gameverse.data.model.Product
import com.gameverse.data.model.UserProfile

/**
 * Representa el estado de la UI para la pantalla de Login.
 */
data class LoginUiState(
    val isLoading: Boolean = false,
    val loginSuccess: Boolean = false,
    val error: String? = null
)

/**
 * Representa el estado de la UI para las pantallas principales que comparten datos.
 */
data class MainUiState(
    val isLoading: Boolean = true,
    val products: List<Product> = emptyList(),
    val news: List<NewsItem> = emptyList(),
    val homeHighlights: List<NewsItem> = emptyList(),
    val userProfile: UserProfile? = null,
    val error: String? = null
)

/**
 * Representa el estado de la UI para la pantalla del Carrito de Compras.
 */
data class CartUiState(
    val cartItems: List<CartProduct> = emptyList(),
    val total: Double = 0.0,
    val paymentSuccess: Boolean = false
)