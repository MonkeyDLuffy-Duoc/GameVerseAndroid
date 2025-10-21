package com.gameverse.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gameverse.ui.state.MainUiState
import com.gameverse.data.repository.AppRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel para las pantallas principales que comparten datos (Home, Productos, Noticias, Perfil).
 */
class MainViewModel(
    private val repository: AppRepository = AppRepository // Inyección de dependencia simple
) : ViewModel() {

    // 1. Backing property: Estado mutable, privado para que solo el ViewModel lo modifique.
    private val _uiState = MutableStateFlow(MainUiState(isLoading = true))

    // 2. Estado público: Expuesto como StateFlow inmutable para que la UI lo observe.
    //    La pantalla (ProductsScreen) leerá desde aquí.
    val uiState = _uiState.asStateFlow()

    init {
        // 3. El bloque init se ejecuta en cuanto se crea el ViewModel.
        //    Llamamos a la función que carga todos los datos necesarios.
        loadAllData()
    }

    private fun loadAllData() {
        viewModelScope.launch {
            try {
                // Simulamos una carga de red para que el loader sea visible.
                delay(2000L)

                // 4. Obtenemos todos los datos del repositorio.
                val products = repository.getProducts()
                val news = repository.getNews()
                val homeHighlights = repository.getHomeHighlights()
                val userProfile = repository.getUserProfile()

                // 5. Actualizamos el estado una sola vez con todos los datos nuevos
                //    y ponemos isLoading en 'false'. La UI reaccionará a este cambio.
                _uiState.value = MainUiState(
                    products = products,
                    news = news,
                    homeHighlights = homeHighlights,
                    userProfile = userProfile,
                    isLoading = false // <-- ¡Importante! Aquí desactivamos el loader.
                )
            } catch (e: Exception) {
                // En una app real, manejaríamos el error aquí.
                _uiState.value = _uiState.value.copy(isLoading = false, error = "No se pudieron cargar los datos")
            }
        }
    }

    // Función para cerrar sesión (ejemplo)
    fun logout() {
        // Lógica para cerrar sesión
    }
}