package com.gameverse.ui.screens.login

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.gameverse.ui.components.FullScreenLoader
import com.gameverse.ui.components.LogoImage
import com.gameverse.ui.components.NeonButton
import com.gameverse.ui.components.NeonTextField
import com.gameverse.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel,
    onLoginSuccess: () -> Unit
) {
    // 1. Observa el estado del ViewModel.
    val uiState by loginViewModel.uiState.collectAsState()

    // 2. Estados locales para guardar el contenido de los campos de texto.
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // 3. Efecto para navegar cuando el login es exitoso.
    LaunchedEffect(uiState.loginSuccess) {
        if (uiState.loginSuccess) {
            onLoginSuccess()
        }
    }

    // 4. ¡CAMBIO CLAVE! Usamos un Box en lugar de una Column.
    //    Un Box nos permite alinear elementos de forma independiente.
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp) // Mantenemos el padding horizontal
    ) {

        // 5. EL LOGO:
        //    Lo alineamos en la parte superior central (TopCenter)
        //    y le damos un padding para separarlo del borde.
        LogoImage(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 80.dp) // <-- ¡Mueve el logo hacia arriba!
        )

        // 6. LOS CAMPOS DE TEXTO Y EL BOTÓN:
        //    Los agrupamos en su propia Column
        //    y alineamos esa Column en el centro (Center).
        Column(
            modifier = Modifier.align(Alignment.Center), // <-- ¡Centra este bloque!
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            NeonTextField(
                value = username,
                onValueChange = { username = it },
                label = "Usuario"
            )
            Spacer(modifier = Modifier.height(16.dp))
            NeonTextField(
                value = password,
                onValueChange = { password = it },
                label = "Contraseña",
                keyboardType = KeyboardType.Password
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Muestra el mensaje de error si existe
            uiState.error?.let { errorMessage ->
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            NeonButton(
                onClick = { loginViewModel.login(username, password) },
                text = "Iniciar Sesión",
                enabled = !uiState.isLoading // El botón se deshabilita mientras carga
            )
        }

        // 7. EL LOADER:
        //    Se muestra encima de todo cuando está cargando.
        if (uiState.isLoading) {
            FullScreenLoader()
        }
    }
}

