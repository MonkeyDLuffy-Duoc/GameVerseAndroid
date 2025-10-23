package com.gameverse.ui.screens.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.gameverse.ui.navigation.BottomNavItems
import com.gameverse.ui.screens.home.HomeScreen
import com.gameverse.ui.screens.news.NewsScreen
import com.gameverse.ui.screens.products.ProductsScreen
import com.gameverse.ui.screens.profile.ProfileScreen
import com.gameverse.viewmodel.CartViewModel
import com.gameverse.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenContainer(
    mainViewModel: MainViewModel,
    cartViewModel: CartViewModel,
    onNavigateToCart: () -> Unit
) {
    val bottomNavController = rememberNavController()

    // 1. Observamos el estado del ViewModel principal
    val uiState by mainViewModel.uiState.collectAsState()

    // 2. Obtenemos el nombre de usuario (es 'nullable', puede que aún no haya cargado)
    val username = uiState.userProfile?.username

    // 3. Creamos el texto del título dinámicamente
    val titleText = if (username != null) "¡Hola, $username!" else "Gameverse"

    Scaffold(
        topBar = {
            TopAppBar(
                // 4. Usamos el nuevo texto del título
                title = {
                    Text(
                        text = titleText,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                actions = {
                    IconButton(onClick = onNavigateToCart) {
                        Icon(Icons.Default.ShoppingCart, contentDescription = "Carrito de compras")
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by bottomNavController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                val items = listOf(
                    BottomNavItems.Home,
                    BottomNavItems.Products,
                    BottomNavItems.News,
                    BottomNavItems.Profile,
                )
                items.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = screen.title) },
                        label = { Text(screen.title) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            bottomNavController.navigate(screen.route) {
                                popUpTo(bottomNavController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = bottomNavController,
            startDestination = BottomNavItems.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(BottomNavItems.Home.route) { HomeScreen(mainViewModel) }
            composable(BottomNavItems.Products.route) { ProductsScreen(mainViewModel, cartViewModel) }
            composable(BottomNavItems.News.route) { NewsScreen(mainViewModel) }
            composable(BottomNavItems.Profile.route) { ProfileScreen(mainViewModel) }
        }
    }
}

