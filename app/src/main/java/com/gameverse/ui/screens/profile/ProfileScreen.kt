package com.gameverse.ui.screens.profile

import android.Manifest
import android.annotation.SuppressLint
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.Priority
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
// imports ubicacion
import com.gameverse.viewmodel.UbicacionViewModel
// imports selector imagenes
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.gameverse.viewmodel.SelectorImagenViewModel

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.gameverse.ui.components.FullScreenLoader
import com.gameverse.ui.theme.TextSecondary
import com.gameverse.viewmodel.MainViewModel

@OptIn(ExperimentalPermissionsApi::class) // 👈 Anotación para la API de permisos
@SuppressLint("MissingPermission")      // 👈 Anotación para suprimir advertencias de permisos
@Composable
fun ProfileScreen(
    mainViewModel: MainViewModel = viewModel(),
    // 1. Obtenemos una instancia del ViewModel de ubicación
    ubicacionViewModel: UbicacionViewModel = viewModel(),
    selectorImagenViewModel: SelectorImagenViewModel = viewModel()

) {
    val uiState by mainViewModel.uiState.collectAsState()
    val user = uiState.userProfile

    // 2. Lógica para manejar permisos y obtener la ubicación (igual que antes)
    val contexto = LocalContext.current
    val permisoUbicacion = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)
    val tienePermiso = permisoUbicacion.status is PermissionStatus.Granted

    if (tienePermiso) {
        // DisposableEffect se asegura de que la ubicación se pida solo cuando
        // sea necesario y se detenga cuando la pantalla ya no esté visible.
        DisposableEffect(Unit) {
            val proveedorUbicacion = LocationServices.getFusedLocationProviderClient(contexto)
            val callbackUbicacion = object : LocationCallback() {
                override fun onLocationResult(resultado: LocationResult) {
                    resultado.lastLocation?.let {
                        // Cuando obtenemos una ubicación, actualizamos el ViewModel
                        ubicacionViewModel.actualizarUbicacion(it.latitude, it.longitude)
                    }
                }
            }
            val solicitudUbicacion = LocationRequest.Builder(
                Priority.PRIORITY_HIGH_ACCURACY, 10000L).build()

            proveedorUbicacion.requestLocationUpdates(solicitudUbicacion, callbackUbicacion, null)

            // Se ejecuta cuando el composable se va, para dejar de pedir la ubicación
            onDispose {
                proveedorUbicacion.removeLocationUpdates(callbackUbicacion)
            }
        }
    }

    val lanzadorGaleria = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        // Cuando el usuario elige una imagen, su URI se guarda en el ViewModel
        uri?.let {
            selectorImagenViewModel.asignarUriImagen(it.toString())
        }
    }


    if (uiState.isLoading) {
        FullScreenLoader()
    } else if (user != null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // imagen dinámica
            AsyncImage(
                // Si hay una URI en 'selectorImagenViewModel', la usamos.
                // Si no, usamos la URL original del perfil.
                model = selectorImagenViewModel.uriImagen ?: user.avatarUrl,
                contentDescription = "Avatar de ${user.username}",
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(16.dp))

            // 4. FOTO: Añadimos un botón para cambiar la imagen
            Button(onClick = { lanzadorGaleria.launch("image/*") }) {
                Text("Cambiar foto")
            }
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = user.fullName,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "@${user.username}",
                fontSize = 20.sp,
                color = TextSecondary
            )

            // --- 3. INICIO: Sección de Ubicación integrada en la UI ---
            Spacer(modifier = Modifier.height(32.dp))
            Divider() // Un separador visual
            Spacer(modifier = Modifier.height(32.dp))

            if (!tienePermiso) {
                Button(onClick = { permisoUbicacion.launchPermissionRequest() }) {
                    Text("Activar ubicación")
                }
            } else {
                Text(
                    text = "¡Manten tu ubicación actualizada para que nuestros productos lleguen siempre a tu destino!",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Ubicación actual",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                // Mostramos la dirección obtenida del ViewModel
                Text(
                    text = ubicacionViewModel.direccion ?: "Obteniendo...",
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            // --- FIN: Sección de Ubicación ---
        }
    }
}