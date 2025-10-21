package com.gameverse.viewmodel

import androidx.lifecycle.ViewModel
import com.gameverse.data.model.CartProduct
import com.gameverse.data.model.Product
import com.gameverse.ui.state.CartUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * ViewModel que gestiona toda la lógica del carrito de compras.
 * Este código está diseñado para funcionar con el CartScreen proporcionado.
 */
class CartViewModel : ViewModel() {

    // Estado interno mutable. Solo el ViewModel puede modificarlo.
    private val _uiState = MutableStateFlow(CartUiState())
    // Estado público inmutable para que la UI lo observe.
    val uiState = _uiState.asStateFlow()

    /**
     * Añade un producto al carrito.
     * Es llamado desde la pantalla de Productos.
     */
    fun addToCart(product: Product) {
        // Convierte un Product en un CartProduct
        val cartProduct = CartProduct(
            id = product.id,
            name = product.name,
            price = product.price,
            imageUrl = product.imageUrl
        )
        // Actualiza el estado de forma segura
        _uiState.update { currentState ->
            // Crea una nueva lista añadiendo el nuevo producto
            val updatedItems = currentState.cartItems + cartProduct
            // Devuelve una copia del estado con la lista y el total actualizados
            currentState.copy(
                cartItems = updatedItems,
                total = recalculateTotal(updatedItems)
            )
        }
    }

    /**
     * Elimina un producto del carrito usando su ID.
     * Esta es una de las funciones que tu CartScreen necesita.
     */
    fun removeFromCart(productId: Int) {
        _uiState.update { currentState ->
            // Crea una nueva lista filtrando el producto a eliminar
            val updatedItems = currentState.cartItems.filterNot { it.id == productId }
            currentState.copy(
                cartItems = updatedItems,
                total = recalculateTotal(updatedItems)
            )
        }
    }

    /**
     * Simula el proceso de pago.
     * Limpia el carrito y establece 'paymentSuccess' en true para que la UI pueda reaccionar.
     */
    fun checkout() {
        // Resetea el estado a uno nuevo, vacío, pero con paymentSuccess = true
        _uiState.value = CartUiState(paymentSuccess = true)
    }

    /**
     * Resetea el estado de 'paymentSuccess' a 'false'.
     * Tu CartScreen llama a esta función después de mostrar el Toast
     * para evitar que se muestre de nuevo si la pantalla se recompone.
     */
    fun resetPaymentStatus() {
        _uiState.update { currentState ->
            currentState.copy(paymentSuccess = false)
        }
    }

    /**
     * Función privada para recalcular el total del carrito cada vez que cambia.
     */
    private fun recalculateTotal(items: List<CartProduct>): Double {
        return items.sumOf { it.price }
    }
}

