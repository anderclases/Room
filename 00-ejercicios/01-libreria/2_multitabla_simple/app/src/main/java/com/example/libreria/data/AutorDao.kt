package com.example.libreria.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Delete
import androidx.room.Update
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AutorDao {

    @Insert
    suspend fun insert(autor: Autor)

    @Update
    suspend fun update(autor: Autor)

    @Delete
    suspend fun delete(autor: Autor)

    @Query("SELECT * FROM autor")
    fun getAll(): Flow<List<Autor>>

    @Query("SELECT * FROM autor WHERE id = :id")
    fun getById(id: Int): Flow<Autor>


    @Query("""
        SELECT * FROM autor
        WHERE (:valor IS NULL OR nombre LIKE '%' || :valor || '%')
    """)
    fun filtrarAutors(valor: String?): Flow<List<Autor>>


    @Query("DELETE FROM autor WHERE id = :id")
    suspend fun deleteById(id: Int)
}