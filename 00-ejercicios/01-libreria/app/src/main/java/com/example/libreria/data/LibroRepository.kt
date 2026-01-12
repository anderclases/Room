package com.example.libreria.data

import com.example.libreria.Libro
import com.example.libreria.LibroDao
import kotlinx.coroutines.flow.Flow

class LibroRepository(private val libroDao: LibroDao) {

    val todosLosLibros:Flow<List<Libro>> = libroDao.getAllLibros()

    // Insertar un libro
    suspend fun insertarLibro(libro: Libro) {
        libroDao.insert(libro)
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


    fun filtrarLibros(titulo: String?, autor: String?): Flow<List<Libro>> =
        libroDao.filtrarLibros(titulo, autor)



}