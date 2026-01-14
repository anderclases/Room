package com.example.libreria.ui.shared

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.libreria.Autor
import com.example.libreria.Categoria
import com.example.libreria.Libro
import com.example.libreria.MyLog
import com.example.libreria.data.LibreriaRepository
import com.example.libreria.data.entity.VistaLibroCompleto
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class LibreriaViewModel(
    private val repositorio: LibreriaRepository // Inyectamos el repositorio
) : ViewModel() {
    // Para el uso de la visualizacion de datos combinados, util para la ventana principal


    private val _librosFiltrados = MutableStateFlow<List<VistaLibroCompleto>>(emptyList())
    //Al abrirse el programa por primera vez se llama a la funcion todosLosLibros() y carga la lista.
    // Cuando se llama a la funcion filtrarLibros se actualiza esta lista. Solo se llama al aplicarFiltros().

    val librosFiltrados: StateFlow<List<VistaLibroCompleto>> = _librosFiltrados


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

    suspend fun eliminarLibro(libro: Libro){
        MyLog.d("LibreriaViewModel.eliminarLibro: ${libro}")
        repositorio.eliminarLibro(libro)
    }
    suspend fun eliminarLibroConId(libroId: Int){
        MyLog.d("LibreriaViewModel.eliminarLibroConId: ${libroId}")
        repositorio.eliminarLibro(Libro(id=libroId, "",0,0,0))
    }

    fun insertarDatosPrueba() {
        viewModelScope.launch {
            runBlocking {
                val categorias = listOf(
                    Categoria(1, "Ficción", "Obras basadas en la imaginación con elementos narrativos inventados."),
                    Categoria(2, "Distopía", "Sociedades imaginarias indeseables donde el control social es opresivo."),
                    Categoria(3, "Sátira", "Uso del humor, la ironía o el ridículo para criticar vicios o instituciones."),
                    Categoria(4,"Romance","Historias centradas en relaciones amorosas y vínculos emocionales.")
                )

                val autores = listOf(
                    Autor(1, "Gabriel García Márquez", "Colombiana", "06/03/1927"),
                    Autor(2, "George Orwell", "Británica", "25/06/1903"),
                    Autor(3, "Jane Austen", "Británica", "16/12/1775"),
                    Autor(4, "Iria G. Parente", "Española", "29/10/1993")
                )
                categorias.forEach { cat -> repositorio.insertarCategoria(cat) }
                autores.forEach { aut -> repositorio.insertarAutor(aut) }
                delay(500)
                val libros = listOf(
                    Libro(id=1, titulo="Cien Años de Soledad",  publicacion=1967, autor_id=1, categoria_id=1),
                    Libro(id=2, titulo="1984",                  publicacion=1949, autor_id=2, categoria_id=2),
                    Libro(id=3, titulo="Rebelión en la Granja", publicacion=1945, autor_id=2, categoria_id=3),
                    Libro(id=4, titulo="Orgullo y Prejuicio",   publicacion=1813, autor_id=3, categoria_id=4),
                    Libro(id=5, titulo="El orgullo del dragón", publicacion=2019, autor_id=4, categoria_id=1)
                )
                libros.forEach { libro -> repositorio.insertarLibro(libro) }

            }

        }
    }

}