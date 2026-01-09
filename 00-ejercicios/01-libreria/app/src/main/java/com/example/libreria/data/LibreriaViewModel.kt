package com.example.libreria

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class LibreriaViewModel(application: Application) : AndroidViewModel(application) {

    private val libroDao = LibreriaDatabase.getDatabase(application).libroDao()

    fun insertLibro(name: String, price: Double, quantity: Int) {
        val newLibro = Libro(name = name, price = price, quantity = quantity)
        Log.d("LibreriaViewModel", "Nuevo Libro creado: $newLibro")

        viewModelScope.launch(Dispatchers.IO) {
            libroDao.insert(newLibro)
            Log.d("LibreriaViewModel", "Insert realizado")
        }
    }

    fun getLibro(id: Int): Flow<Libro> {
        return libroDao.getLibro(id)
    }

    val allLibros: Flow<List<Libro>> = libroDao.getAllLibros()

    suspend fun delete(libro: Libro){
        libroDao.delete(libro)
    }


    suspend fun update(libro: Libro){
        libroDao.update(libro)
    }

}