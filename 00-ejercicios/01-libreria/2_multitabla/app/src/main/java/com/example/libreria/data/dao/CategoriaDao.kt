package com.example.libreria

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoriaDao {

    @Query("SELECT * from categoria ORDER BY nombre ASC")
    fun getAllcategorias(): Flow<List<Categoria>>

    @Query("SELECT * from categoria WHERE id = :id")
    fun getcategoria(id: Int): Flow<Categoria>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entity: Categoria)

    @Update()
    suspend fun update(entity: Categoria)

    @Delete
    suspend fun delete(entity: Categoria)

}