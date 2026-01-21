package com.example.libreria.ui.shared

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.libreria.data.Libro
import com.example.libreria.MyLog
import com.example.libreria.data.Autor
import com.example.libreria.data.LibroRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class LibroViewModel(
    private val repositorio: LibroRepository // Inyectamos el repositorio
) : ViewModel() {


    //Obtener todos los libros
    //val todosLosLibros: Flow<List<Libro>> = repositorio.todosLosLibros
    private val _librosFiltrados = MutableStateFlow<List<Libro>>(emptyList())
    val librosFiltrados: StateFlow<List<Libro>> = _librosFiltrados

    // Usamos mutableStateOf para que Compose reaccione a los cambios
    var idLibro by mutableIntStateOf(0)
        private set

    var titulo by mutableStateOf("")
        private set

    var autor by mutableStateOf("")
        private set
    var autorId by mutableStateOf("")
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

    fun onAutorIdChanged(nuevoTexto: String) {
        autorId = nuevoTexto
        if (errorMensaje != null) errorMensaje = null
    }
    fun onPublicacionChanged(nuevoTexto: String) {
        publicacion = nuevoTexto
        if (errorMensaje != null) errorMensaje = null
    }

    // --- INSERT Y EDITAR DATOS  ---

    fun guardarLibro(onSuccess: () -> Unit) {
        MyLog.d("LibroViewModel.guardarLibro")
        // 1. Validación básica
        if (titulo.isBlank() || autorId.isBlank()) {
            val msg = "Por favor, completa todos los campos, para guardar correctamente"
            errorMensaje = msg
            MyLog.e(msg)
            return
        }

        // 2. Ejecutar en segundo plano (Corrutina)
        viewModelScope.launch {
            try {
                val nuevoLibro = Libro(
                    titulo = titulo.trim(),
                    autor_id = autorId.toInt(),
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

    fun editarLibro(onSuccess: () -> Unit) {
        if (titulo.isBlank() || autorId.isBlank()) {
            errorMensaje = "Por favor, completa todos los campos, para editar correctamente"
            return
        }

        viewModelScope.launch {
            try {
                val libroActualizado = Libro(
                    id = idLibro,
                    titulo = titulo.trim(),
                    autor_id = autorId.toInt(),
                    published = publicacion.toInt()
                )

                repositorio.actualizarLibro(libroActualizado)

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
                        idLibro = libro.id
                        titulo = libro.titulo
                        autorId = libro.autor_id.toString()
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
                Libro(0, "Cien años de soledad", 1, 1967),
                Libro(0, "El señor de los anillos", 2, 1954),
                Libro(0, "1984", 1, 1949),
            )
            val autores = listOf(
                Autor(0,"George Orwell","Eng","01/01/01"),
                Autor(0,"George Orwell","Eng","01/01/01"),
                Autor(0,"George Orwell","Eng","01/01/01")
            )
            runBlocking {

                autores.forEach { autor ->
                    repositorio.insertarAutor(autor)
                }
                delay(500)
                libros.forEach { libro ->
                    repositorio.insertarLibro(libro)
                }


            }

        }
    }

}