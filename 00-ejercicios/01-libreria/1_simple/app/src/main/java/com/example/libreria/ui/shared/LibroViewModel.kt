package com.example.libreria.ui.shared

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.libreria.Libro
import com.example.libreria.MyLog
import com.example.libreria.data.LibroRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class LibroViewModel(
    private val repositorio: LibroRepository // Inyectamos el repositorio
) : ViewModel() {


    //Obtener todos los libros
    //val todosLosLibros: Flow<List<Libro>> = repositorio.todosLosLibros
    private val _librosFiltrados = MutableStateFlow<List<Libro>>(emptyList())
    val librosFiltrados: StateFlow<List<Libro>> = _librosFiltrados

    // Usamos mutableStateOf para que Compose reaccione a los cambios
    var titulo by mutableStateOf("")
        private set

    var autor by mutableStateOf("")
        private set

    var publicacion by mutableStateOf("")
        private set

    var filtroTitulo by mutableStateOf("")
        private set

    var filtroAutor by mutableStateOf("")
        private set

    // Estado para manejar errores visuales
    var errorMensaje by mutableStateOf<String?>(null)
        private set


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
    fun onTituloChanged(nuevoTexto: String) {
        titulo = nuevoTexto
        if (errorMensaje != null) errorMensaje = null // Limpiar error al escribir
    }

    fun onAutorChanged(nuevoTexto: String) {
        autor = nuevoTexto
        if (errorMensaje != null) errorMensaje = null
    }
    fun onPublicacionChanged(nuevoTexto: String) {
        publicacion = nuevoTexto
        if (errorMensaje != null) errorMensaje = null
    }

    // --- LÓGICA DE NEGOCIO ---

    fun guardarLibro(onSuccess: () -> Unit) {
        // 1. Validación básica
        if (titulo.isBlank() || autor.isBlank()) {
            errorMensaje = "Por favor, completa todos los campos"
            return
        }

        // 2. Ejecutar en segundo plano (Corrutina)
        viewModelScope.launch {
            try {
                val nuevoLibro = Libro(
                    titulo = titulo.trim(),
                    autor = autor.trim(),
                    published = publicacion.toInt()
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
        if (titulo.isBlank() || autor.isBlank()) {
            errorMensaje = "Por favor, completa todos los campos"
            return
        }

        viewModelScope.launch {
            try {
                // 2. Construir el objeto actualizado manteniendo el id
                val libroActualizado = Libro(
                    id = libroId,
                    titulo = titulo.trim(),
                    autor = autor.trim(),
                    published = publicacion.toInt()
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



    private fun limpiarFormulario() {
        titulo = ""
        autor = ""
        publicacion = ""
        errorMensaje = null
    }

    /** Observa el libro por id y actualiza los campos del ViewModel continuamente */
    fun observarLibro(id: Int) {
        viewModelScope.launch {
            try {
                repositorio.getLibro(id)                // Flow<Libro>
                    .distinctUntilChanged()             // evita actualizaciones idénticas
                    .collect { libro ->
                        titulo = libro.titulo
                        autor = libro.autor
                        publicacion = libro.published.toString()
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