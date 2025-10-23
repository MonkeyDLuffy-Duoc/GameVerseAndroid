package com.gameverse.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class SelectorImagenViewModel : ViewModel() {
    var uriImagen by mutableStateOf<String?>(null)
        private set

    fun asignarUriImagen(uri: String?) {
        uriImagen = uri
    }
}