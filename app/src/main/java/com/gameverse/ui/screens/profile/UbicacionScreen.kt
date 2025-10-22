package com.gameverse.ui.screens.profile

import android.Manifest
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import androidx.compose.runtime.DisposableEffect
import com.google.android.gms.location.LocationServices
// 👇 NECESARIO: Nuevas importaciones para la solicitud continua de ubicación
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.Priority
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult

import com.gameverse.viewmodel.UbicacionViewModel

@OptIn(ExperimentalPermissionsApi::class)
@SuppressLint("MissingPermission")
@Composable
fun UbicacionScreen(
    viewModel: UbicacionViewModel = viewModel()
) {
    val contexto = LocalContext.current
    val permisoUbicacion = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)
    val tienePermiso = permisoUbicacion.status is PermissionStatus.Granted

    // 💡 SOLUCIÓN 1: Declarar el proveedor de ubicación
    val proveedorUbicacion = LocationServices.getFusedLocationProviderClient(contexto)

    if (tienePermiso) {
        // 💡 SOLUCIÓN 3: Usamos 'Unit' para que se ejecute solo una vez cuando tienePermiso sea true
        DisposableEffect(Unit) {

            val callbackUbicacion = object : LocationCallback() { // Usamos LocationCallback importado
                override fun onLocationResult(resultado: LocationResult) {
                    val ubicacion = resultado.lastLocation
                    if (ubicacion != null) {
                        viewModel.actualizarUbicacion(ubicacion.latitude, ubicacion.longitude)
                    }
                }
            }

            // 💡 SOLUCIÓN 2: Usamos las clases LocationRequest y Priority importadas
            val solicitudUbicacion = LocationRequest.Builder(
                Priority.PRIORITY_HIGH_ACCURACY, 5000L)
                .setMinUpdateIntervalMillis(2000L)
                .build()

            proveedorUbicacion.requestLocationUpdates(
                solicitudUbicacion,
                callbackUbicacion,
                null
            )

            // Función de limpieza: Detiene las actualizaciones de ubicación
            onDispose {
                proveedorUbicacion.removeLocationUpdates(callbackUbicacion)
            }
        }
    }

    Column {
        Button(onClick = {
            permisoUbicacion.launchPermissionRequest()
        }) {
            Text("Solicitar permiso de ubicación")
        }

        Text("Latitud: ${viewModel.latitud ?: "-"}")
        Text("Longitud: ${viewModel.longitud ?: "-"}")

        Text("Dirección: ${viewModel.direccion ?: "Buscando..."}")
    }
}