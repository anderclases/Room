package com.example.libreria.ui.shared

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.libreria.Libro
import com.example.libreria.data.LibreriaRepository
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class LibroViewModel(
    private val repositorio: LibreriaRepository
) : ViewModel() {
    // Para el formulario de creacion de libros

    var errorMensaje by mutableStateOf<String?>(null)
        private set

    // ATRIBUTOS
    var titulo by mutableStateOf("")
        private set
    var autor_id by mutableIntStateOf(0)
        private set

    var categoria_id by mutableIntStateOf(0)
        private set

    var publicacion by mutableStateOf("")
        private set


    fun onPublicacionChanged(nuevoTexto: String) {
        publicacion = nuevoTexto
        if (errorMensaje != null) errorMensaje = null
    }

    // LIMPIEZA DE ATRIBUTOS
    private fun limpiarFormulario() {
        titulo = ""
        autor_id = 0
        publicacion = ""
        errorMensaje = null
    }

    fun onTituloChanged(nuevoTexto: String) {
        titulo = nuevoTexto
        if (errorMensaje != null) errorMensaje = null // Limpiar error al escribir
    }

    // --- OPERACIONES BASE DE DATOS ---
    fun guardarLibro(onSuccess: () -> Unit) {
        // 1. Validación básica
        if (titulo.isBlank() || autor_id==0) {
            errorMensaje = "Por favor, completa todos los campos"
            return
        }

        // 2. Ejecutar en segundo plano (Corrutina)
        viewModelScope.launch {
            try {
                val nuevoLibro = Libro(
                    titulo = titulo.trim(),
                    autor_id = autor_id,
                    categoria_id = categoria_id,
                    publicacion = publicacion.toInt()
                )
                repositorio.insertarLibro(nuevoLibro)
                onSuccess()
                limpiarFormulario()
            } catch (e: Exception) {
                errorMensaje = "Error al guardar: ${e.message}"
            }
        }
    }

    fun editarLibro(libroId: Int, onSuccess: () -> Unit) {
        // 1. Validación básica
        if (titulo.isBlank() || autor_id==0) {
            errorMensaje = "Por favor, completa todos los campos"
            return
        }

        viewModelScope.launch {
            try {
                // 2. Construir el objeto actualizado manteniendo el id
                val libroActualizado = Libro(
                    id = libroId,
                    titulo = titulo.trim(),
                    autor_id = autor_id,
                    categoria_id = categoria_id,
                    publicacion = publicacion.toInt()
                )

                // 3. Actualizar en BD (debe existir en el repositorio/DAO)
                repositorio.actualizarLibro(libroActualizado)

                // 4. Notificar éxito y limpiar
                onSuccess()
                limpiarFormulario()
            } catch (e: Exception) {
                errorMensaje = "Error al editar: ${e.message}"
            }
        }
    }

    fun observarLibro(id: Int) {
        viewModelScope.launch {
            try {
                repositorio.getLibro(id)                // Flow<Libro>
                    .distinctUntilChanged()             // evita actualizaciones idénticas
                    .collect { libro ->
                        titulo = libro.titulo
                        autor_id = libro.autor_id
                        publicacion = libro.publicacion.toString()
                    }
            } catch (e: Exception) {
                errorMensaje = "Error al observar libro: ${e.message}"
            }
        }
    }

}