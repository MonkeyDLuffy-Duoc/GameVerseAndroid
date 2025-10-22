package com.gameverse.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope // ¡Importante!
import kotlinx.coroutines.Dispatchers // ¡Importante!
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// CAMBIO 1: Hereda de AndroidViewModel en lugar de ViewModel
// y pasa 'application' al constructor.
class UbicacionViewModel(application: Application) : AndroidViewModel(application) {

    var latitud by mutableStateOf<Double?>(null)
        private set
    var longitud by mutableStateOf<Double?>(null)
        private set

    // Agregamos un nuevo estado para guardar la dirección
    var direccion by mutableStateOf<String?>(null)
        private set

    fun actualizarUbicacion(lat: Double, lon: Double) {
        latitud = lat
        longitud = lon

        // CAMBIO 2: Usa viewModelScope en lugar de lifecycleScope
        viewModelScope.launch {
            // Ponemos un valor temporal mientras se carga
            direccion = "Buscando dirección..."

            // CAMBIO 3: Usa withContext(Dispatchers.IO) para la tarea de red
            val addressText = withContext(Dispatchers.IO) {
                // CAMBIO 4: Usa getApplication() para obtener el contexto y
                // pasa las variables del método (lat, lon)
                getAddressFromCoordinates(getApplication(), lat, lon)
            }

            // El resultado se asigna a nuestro estado 'direccion'
            direccion = addressText
            println("La dirección es: $addressText")
        }
    }
}