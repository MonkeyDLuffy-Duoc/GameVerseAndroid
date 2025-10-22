package com.gameverse.data.repository

import com.gameverse.data.model.NewsItem
import com.gameverse.data.model.Product
import com.gameverse.data.model.UserProfile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Singleton que actúa como la única fuente de datos para la aplicación.
 * Simula llamadas a una red o base de datos.
 */
object AppRepository {

    suspend fun getProducts(): List<Product> = withContext(Dispatchers.IO) {
        // Simula una llamada de red
        listOf(
            Product(1, "Controlador Inalámbrico Élite", "Controlador de alto rendimiento para gaming competitivo.", 99.99, "https://images.unsplash.com/photo-1542751371-adc38448a05e?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwxMTc3M3wwfDF8c2VhcmNofDEwfHxnYW1pbmclMjBjb250cm9sbGVyfGVufDB8fHx8MTY3OTM2ODU2Nw&ixlib=rb-4.0.3&q=80&w=600"),
            Product(2, "Auriculares Gaming 7.1", "Sonido envolvente para una inmersión total.", 79.50, "https://images.unsplash.com/photo-1505740420928-5e560c06d30e?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwxMTc3M3wwfDF8c2VhcmNofDF8fGdhbWluZyUyMGhlYWRzZXR8ZW58MHx8fHwxNjc5MzY4NjE0&ixlib=rb-4.0.3&q=80&w=600"),
            Product(3, "Teclado Mecánico RGB", "Respuesta táctil y retroiluminación personalizable.", 120.00, "https://media.solotodo.com/media/products/1054607_picture_1622047938.jpg"),
            Product(4, "Mouse Gamer Programable", "Sensor de alta precisión y botones configurables.", 45.99, "https://images.unsplash.com/photo-1563297007-0686b7003af7?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwxMTc3M3wwfDF8c2VhcmNofDEyfHxtZWNoYW5pY2FsJTIwa2V5Ym9hcmR8ZW58MHx8fHwxNjc5MzY4NjQz&ixlib=rb-4.0.3&q=80&w=600"),
            Product(5, "Silla Gamer Ergonómica", "Soporte lumbar y diseño de carreras.", 189.99, "https://media.solotodo.com/media/products/2259619_picture_1755859033.png"),
            Product(6, "Webcam Pro 4K", "Videollamadas y streaming en ultra alta definición.", 130.00, "https://media.falabella.com/falabellaCL/116099480_01/w=1500,h=1500,fit=pad")
        )
    }

    suspend fun getNews(): List<NewsItem> = withContext(Dispatchers.IO) {
        // Simula una llamada de red
        listOf(
            NewsItem(1, "Elden Ring: Shadow of the Erdtree", "La esperada expansión de Elden Ring recibe sus primeras imágenes...", "https://media.falabella.com/falabellaCL/133261845_01/w=1500,h=1500,fit=pad"),
            NewsItem(2, "Star Wars Outlaws Revelado", "Ubisoft sorprende con un nuevo juego de mundo abierto de Star Wars...", "https://media.falabella.com/falabellaCL/143652291_01/w=1500,h=1500,fit=pad"),
            NewsItem(3, "Resumen del Nintendo Direct", "Anunciado un nuevo Zelda 2D y el regreso de un clásico de culto...", "https://media.falabella.com/falabellaCL/17524937_1/w=1500,h=1500,fit=pad")
        )
    }

    suspend fun login(user: String, pass: String): Boolean = withContext(Dispatchers.IO) {
        // Simula una validación de red/base de datos.
        user == "Gameverse" && pass == "duoc2025"
    }

    /**
     * Devuelve una lista de noticias destacadas para la pantalla de inicio.
     */
    suspend fun getHomeHighlights(): List<NewsItem> = withContext(Dispatchers.IO) {
        // Para este ejemplo, simplemente devolvemos las mismas noticias.
        getNews()
    }

    suspend fun getUserProfile(): UserProfile = withContext(Dispatchers.IO) {
        // Simula una llamada de red
        UserProfile(
            username = "Gameverse",
            fullName = "Usuario de Prueba",
            email = "contacto@gameverse.dev",
            memberSince = "Octubre 2025",
            avatarUrl = "https://images.unsplash.com/photo-1531746790731-6c087fecd65a?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwxMTc3M3wwfDF8c2VhcmNofDEyfHxwcm9maWxlJTIwZmVtYWxlfGVufDB8fHx8MTY3OTM2ODkyOA&ixlib=rb-4.0.3&q=80&w=300"
        )
    }
}

