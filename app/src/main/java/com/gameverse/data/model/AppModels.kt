package com.gameverse.data.model

/**
 * Representa un producto que se puede vender en la tienda.
 */
data class Product(
    val id: Int,
    val name: String,
    val details: String,
    val price: Double,
    val imageUrl: String
)

/**
 * Representa un producto cuando ya ha sido añadido al carrito de compras.
 * Es muy similar a 'Product', pero lo separamos por si en el futuro
 * queremos añadir propiedades específicas del carrito, como la cantidad.
 */
data class CartProduct(
    val id: Int,
    val name: String,
    val price: Double,
    val imageUrl: String
)

/**
 * Representa un artículo de noticias o un destacado en la pantalla de inicio.
 */
data class NewsItem(
    val id: Int,
    val title: String,
    val summary: String,
    val imageUrl: String
)

/**
 * Representa la información del perfil del usuario.
 */
data class UserProfile(
    val username: String,
    val fullName: String,
    val email: String,
    val memberSince: String,
    val avatarUrl: String
)
