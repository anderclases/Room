package com.example.libreria.data

import com.example.libreria.Autor
import com.example.libreria.AutorDao
import com.example.libreria.Categoria
import com.example.libreria.CategoriaDao
import com.example.libreria.Libro
import com.example.libreria.data.dao.LibroDao
import com.example.libreria.data.entity.VistaLibroCompleto
import kotlinx.coroutines.flow.Flow

class LibreriaRepository(
    private val libroDao: LibroDao,
    private val autorDao: AutorDao,
    private val categoriaDao: CategoriaDao
) {

    val todosLosLibros:Flow<List<VistaLibroCompleto>> = libroDao.getAllLibros()
    val todosLosAutores:Flow<List<Autor>> = autorDao.getAllAutors()

    // Insertar un libro
    suspend fun insertarLibro(libro: Libro) {
        libroDao.insert(libro)
    }

    suspend fun insertarAutor(autor: Autor) {
        autorDao.insert(autor)
    }
    suspend fun insertarCategoria(cat: Categoria) {
        categoriaDao.insert(cat)
    }

    fun getLibro(id: Int): Flow<Libro> {
        return libroDao.getLibro(id)
    }

    // Borrar un libro
    suspend fun eliminarLibro(libro: Libro) {
        libroDao.delete(libro)
    }

    // Actualizar un libro existente
    suspend fun actualizarLibro(libro: Libro) {
        libroDao.update(libro)
    }

    fun filtrarLibros(titulo: String?, autor: String?): Flow<List<VistaLibroCompleto>> =
        libroDao.filtrarLibros(titulo, autor)

}