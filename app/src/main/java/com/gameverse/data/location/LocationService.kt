package com.gameverse.data.location

import android.annotation.SuppressLint
import android.content.Context
import com.gameverse.viewmodel.UbicacionViewModel
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.Priority // Importar esto es crucial para LocationRequest

@SuppressLint("MissingPermission")
fun iniciarActualizacionesUbicacion(contexto: Context, viewModel: UbicacionViewModel) {
    val proveedorUbicacion = LocationServices.getFusedLocationProviderClient(contexto)

    // 1. Crear la solicitud de ubicación (LocationRequest)
    val solicitudUbicacion = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 5000)
        .setMinUpdateIntervalMillis(2000) // Opcional: Intervalo más rápido para el emulador
        .build()

    // 2. Definir el Callback para manejar las actualizaciones
    val callbackUbicacion = object : com.google.android.gms.location.LocationCallback() {
        override fun onLocationResult(resultado: com.google.android.gms.location.LocationResult) {
            // Se llama cada vez que el proveedor de ubicación tiene una nueva coordenada
            val ubicacion = resultado.lastLocation
            if (ubicacion != null) {
                // 3. Actualizar el ViewModel con el nuevo valor
                viewModel.actualizarUbicacion(ubicacion.latitude, ubicacion.longitude)
            }
        }
    }
}