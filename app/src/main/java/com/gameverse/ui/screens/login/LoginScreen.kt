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
    // 1. Observa el estado del ViewModel. La UI se recompone automáticamente cuando cambia.
    val uiState by loginViewModel.uiState.collectAsState()

    // 2. Estados locales para guardar el contenido de los campos de texto.
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // 3. 'LaunchedEffect' se ejecuta cuando 'uiState.loginSuccess' cambia.
    //    Si es 'true', llama a la función para navegar a la pantalla principal.
    LaunchedEffect(uiState.loginSuccess) {
        if (uiState.loginSuccess) {
            onLoginSuccess()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LogoImage()
            Spacer(modifier = Modifier.height(48.dp))
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

            // 4. Muestra el mensaje de error solo si 'uiState.error' no es nulo.
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

        // 5. Muestra el loader de pantalla completa si 'uiState.isLoading' es 'true'.
        if (uiState.isLoading) {
            FullScreenLoader()
        }
    }
}

