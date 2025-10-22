package com.gameverse.viewmodel

import android.content.Context
import android.location.Geocoder
import java.io.IOException
import java.util.Locale

// --- Coloca esta función en alguna parte de tu código, como en un ViewModel o una clase de utilidad ---

/**
 * Función que convierte coordenadas en una dirección (Ciudad, País).
 * Es una 'suspend function', por lo que debe llamarse desde una corutina.
 *
 * @param context El contexto de la aplicación.
 * @param latitude La latitud.
 * @param longitude La longitud.
 * @return Un String con "Ciudad, País" o un mensaje de error si no se encuentra.
 */


suspend fun getAddressFromCoordinates(context: Context, latitude: Double, longitude: Double): String {
    // 1. Crea una instancia de Geocoder
    val geocoder = Geocoder(context, Locale.getDefault())

    return try {
        // 2. Llama a getFromLocation. El '1' indica que solo queremos el primer resultado.
        // En Android 13 (API 33) y superior, esta llamada es asíncrona y necesita un listener.
        // Para versiones anteriores, es una llamada síncrona que bloquea el hilo,
        // por eso es crucial usarla dentro de una corutina con Dispatchers.IO.
        val addresses = geocoder.getFromLocation(latitude, longitude, 1)

        if (addresses != null && addresses.isNotEmpty()) {
            val address = addresses[0]
            // 3. Extrae la ciudad (locality) y el país (countryName)
            val city = address.locality ?: "Ciudad no encontrada"
            val country = address.countryName ?: "País no encontrado"
            "$city, $country" // Devuelve el string formateado
        } else {
            "Dirección no encontrada"
        }
    } catch (e: IOException) {
        // Este error ocurre si no hay conexión a internet o el servicio no está disponible.
        "Error al obtener la dirección: ${e.message}"
    }
}