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
            Product(1, "Controlador Inalámbrico Élite", "Controlador de alto rendimiento para gaming competitivo.", 99.99, "https://placehold.co/600x400/212121/00BCD4?text=Control"),
            Product(2, "Auriculares Gaming 7.1", "Sonido envolvente para una inmersión total.", 79.50, "https://placehold.co/600x400/212121/00BCD4?text=Auriculares"),
            Product(3, "Teclado Mecánico RGB", "Respuesta táctil y retroiluminación personalizable.", 120.00, "https://placehold.co/600x400/212121/00BCD4?text=Teclado"),
            Product(4, "Mouse Gamer Programable", "Sensor de alta precisión y botones configurables.", 45.99, "https://placehold.co/600x400/212121/00BCD4?text=Mouse")
        )
    }

    suspend fun getNews(): List<NewsItem> = withContext(Dispatchers.IO) {
        // Simula una llamada de red
        listOf(
            NewsItem(1, "Lanzamiento del Año: 'Cyber Odyssey'", "El esperado RPG de ciencia ficción llega a las tiendas este mes...", "https://placehold.co/400x400/212121/FFFFFF?text=Noticia+1"),
            NewsItem(2, "Actualización Mayor para 'Arena Kings'", "El popular shooter recibe un nuevo mapa, un nuevo personaje y...", "https://placehold.co/400x400/212121/FFFFFF?text=Noticia+2"),
            NewsItem(3, "Reseña de la nueva consola 'NextGen+'", "Analizamos a fondo la nueva máquina que promete revolucionar el...", "https://placehold.co/400x400/212121/FFFFFF?text=Noticia+3")
        )
    }

    suspend fun login(user: String, pass: String): Boolean = withContext(Dispatchers.IO) {
        // Simula una validación de red/base de datos.
        user == "Gameverse" && pass == "duoc2025"
    }

    /**
     * ¡ESTA ES LA FUNCIÓN QUE FALTABA!
     * Devuelve una lista de noticias destacadas para la pantalla de inicio.
     */
    suspend fun getHomeHighlights(): List<NewsItem> = withContext(Dispatchers.IO) {
        // Para este ejemplo, simplemente devolvemos las mismas noticias.
        // En una app real, aquí podrías tener una lógica diferente.
        getNews()
    }

    suspend fun getUserProfile(): UserProfile = withContext(Dispatchers.IO) {
        // Simula una llamada de red
        UserProfile(
            username = "Gameverse",
            fullName = "Usuario de Prueba",
            email = "contacto@gameverse.dev",
            memberSince = "Octubre 2025",
            avatarUrl = "https://placehold.co/300x300/212121/00BCD4?text=GV"
        )
    }
}

