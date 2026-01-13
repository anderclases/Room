package com.example.libreria.ui.shared

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.libreria.data.LibreriaRepository

class AutorViewModel(
    private val repositorio: LibreriaRepository
) : ViewModel() {
    // Para el formulario de la creacion de autores
    var nombre by mutableStateOf("")
        private set
    var nacionalidad by mutableStateOf("")
        private set
    var fechaNacimiento by mutableStateOf("")
        private set

}