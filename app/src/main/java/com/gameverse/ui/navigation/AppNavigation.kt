package com.gameverse.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gameverse.ui.screens.cart.CartScreen
import com.gameverse.ui.screens.login.LoginScreen
import com.gameverse.ui.screens.main.MainScreenContainer
import com.gameverse.viewmodel.CartViewModel
import com.gameverse.viewmodel.LoginViewModel
import com.gameverse.viewmodel.MainViewModel

@Composable
fun AppNavigation() {
    // 1. Crea el controlador de navegación que gestionará el cambio entre pantallas.
    val navController = rememberNavController()

    // 2. Crea instancias de los ViewModels que se compartirán entre múltiples pantallas.
    val loginViewModel: LoginViewModel = viewModel()
    val mainViewModel: MainViewModel = viewModel()
    val cartViewModel: CartViewModel = viewModel()

    // 3. 'NavHost' es el contenedor que mostrará la pantalla actual según la ruta.
    NavHost(navController = navController, startDestination = "login") {

        // 4. Define la pantalla para la ruta "login".
        composable("login") {
            LoginScreen(
                loginViewModel = loginViewModel,
                onLoginSuccess = {
                    // Acción a ejecutar cuando el login sea exitoso.
                    // Navega a la pantalla "main" y limpia la pila de navegación
                    // para que el usuario no pueda volver atrás al login con el botón de retroceso.
                    navController.navigate("main") {
                        popUpTo("login") {
                            inclusive = true
                        }
                    }
                }
            )
        }

        // 5. Define la pantalla para la ruta "main".
        //    Esta pantalla es el contenedor principal que tiene la barra de navegación inferior.
        composable("main") {
            MainScreenContainer(
                mainViewModel = mainViewModel,
                cartViewModel = cartViewModel,
                onNavigateToCart = {
                    // Acción para navegar desde cualquier parte de la app al carrito.
                    navController.navigate("cart")
                }
            )
        }

        // 6. Define la pantalla para la ruta "cart".
        composable("cart") {
            CartScreen(cartViewModel = cartViewModel)
        }
    }
}

