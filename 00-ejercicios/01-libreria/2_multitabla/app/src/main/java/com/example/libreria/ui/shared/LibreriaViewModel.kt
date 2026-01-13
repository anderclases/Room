package com.example.libreria.ui.shared

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.libreria.Libro
import com.example.libreria.MyLog
import com.example.libreria.data.LibreriaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class LibreriaViewModel(
    private val repositorio: LibreriaRepository // Inyectamos el repositorio
) : ViewModel() {
    // Para el uso de la visualizacion de datos combinados, util para la ventana principal
    private val _librosFiltrados = MutableStateFlow<List<Libro>>(emptyList())
    val librosFiltrados: StateFlow<List<Libro>> = _librosFiltrados

    // Estado para manejar errores visuales
    var errorMensaje by mutableStateOf<String?>(null)
        private set

    // ATRIBUTOS
    var tituloLibro by mutableStateOf("")
        private set

    var nombreAutor by mutableStateOf("")
        private set

    var publicacion by mutableStateOf("")
        private set

    var filtroTitulo by mutableStateOf("")
        private set

    var filtroAutor by mutableStateOf("")
        private set

    // LIMPIEZA DE ATRIBUTOS

    private fun limpiarFormulario() {
        tituloLibro = ""
        nombreAutor = ""
        publicacion = ""
        errorMensaje = null
    }


    init {
        viewModelScope.launch {
            repositorio.todosLosLibros.collect { lista ->
                _librosFiltrados.value = lista
            }
        }
    }


    // --- FUNCIONES DE CAMBIO (Eventos) ---

    fun aplicarFiltros(titulo: String, autor: String) {
        viewModelScope.launch {
            if(filtroTitulo.isEmpty() && filtroAutor.isEmpty()) {
                repositorio.todosLosLibros.collect { lista ->
                    _librosFiltrados.value = lista
                }
            } else {
                repositorio.filtrarLibros(titulo, autor).collect { lista ->
                    _librosFiltrados.value = lista
                }
            }
        }
    }

    fun onFiltroAutorChanged(nuevoTexto: String) {
        filtroAutor = nuevoTexto
        if (errorMensaje != null) errorMensaje = null // Limpiar error al escribir
    }

    fun onFiltroTituloChanged(nuevoTexto: String) {
        filtroTitulo = nuevoTexto
        if (errorMensaje != null) errorMensaje = null // Limpiar error al escribir
    }

    fun onAutorChanged(nuevoTexto: String) {
        nombreAutor = nuevoTexto
        if (errorMensaje != null) errorMensaje = null
    }
    fun onPublicacionChanged(nuevoTexto: String) {
        publicacion = nuevoTexto
        if (errorMensaje != null) errorMensaje = null
    }

    // --- OPERACIONES BASE DE DATOS ---

    /** Observa el libro por id y actualiza los campos del ViewModel continuamente */
    fun observarLibro(id: Int) {
        viewModelScope.launch {
            try {
                repositorio.getLibro(id)                // Flow<Libro>
                    .distinctUntilChanged()             // evita actualizaciones idénticas
                    .collect { libro ->
                        tituloLibro = libro.titulo
                        nombreAutor = libro.autor
                        publicacion = libro.publicacion.toString()
                    }
            } catch (e: Exception) {
                errorMensaje = "Error al observar libro: ${e.message}"
            }
        }
    }

    suspend fun eliminarLibro(libroId: Int){
        MyLog.d("Solicitud eliminar")
        repositorio.eliminarLibro(Libro(libroId))
    }

    fun insertarDatosPrueba() {
        viewModelScope.launch {
            val libros = listOf(
                Libro(0, "Cien años de soledad", "García Márquez", 1967),
                Libro(0, "El señor de los anillos", "Tolkien", 1954),
                Libro(0, "1984", "George Orwell", 1949),
                Libro(0, "El nombre del viento", "Patrick Rothfuss", 2007),
                Libro(0, "Crónica de una muerte anunciada", "García Márquez", 1981),
                Libro(0, "La sombra del viento", "Carlos Ruiz Zafón", 2001),
                Libro(0, "Fahrenheit 451", "Ray Bradbury", 1953),
                Libro(0, "Juego de tronos", "George R. R. Martin", 1996),
                Libro(0, "El Principito", "Antoine de Saint-Exupéry", 1943),
                Libro(0, "El Código Da Vinci", "Dan Brown", 2003)
            )

            libros.forEach { libro ->
                repositorio.insertarLibro(libro)
            }
        }
    }

}