package com.example.libreria.ui.shared

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.libreria.Autor
import com.example.libreria.Libro
import com.example.libreria.data.LibreriaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class AutorViewModel(
    private val repositorio: LibreriaRepository
) : ViewModel() {
    // Para el formulario de la creacion de autores

    var errorMensaje by mutableStateOf<String?>(null)
        private set

    var nombre by mutableStateOf("")
        private set
    var nacionalidad by mutableStateOf("")
        private set
    var fechaNacimiento by mutableStateOf("")
        private set

    fun onNombreChanged(nuevoTexto: String) {
        nombre = nuevoTexto
        if (errorMensaje != null) errorMensaje = null
    }

    fun onNacionalidadChanged(nuevoTexto: String) {
        nacionalidad = nuevoTexto
        if (errorMensaje != null) errorMensaje = null
    }

    fun onFechaNacimientoChanged(nuevoTexto: String) {
        fechaNacimiento = nuevoTexto
        if (errorMensaje != null) errorMensaje = null
    }

    private fun limpiarFormulario() {
        nombre = ""
        nacionalidad = ""
        fechaNacimiento = ""
        errorMensaje = null
    }

    val todosLosAutores: Flow<List<Autor>> = repositorio.todosLosAutores


    // --- INSERT Y EDITAR DATOS  ---
    fun guardarAutor(onSuccess: () -> Unit) {
        // 1. Validación básica
        if (nombre.isBlank() || nacionalidad.isBlank() || fechaNacimiento.isBlank()) {
            errorMensaje = "Por favor, completa todos los campos"
            return
        }

        // 2. Ejecutar en segundo plano (Corrutina)
        viewModelScope.launch {
            try {
                val nuevoAutor = Autor(
                    nombre = nombre.trim(),
                    nacionalidad = nacionalidad.trim(),
                    fechaNacimiento = fechaNacimiento.trim()
                )

                repositorio.insertarAutor(nuevoAutor)

                onSuccess()
                limpiarFormulario()
            } catch (e: Exception) {
                errorMensaje = "Error al guardar: ${e.message}"
            }
        }
    }
}