package com.example.libreria

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface AutorDao {

    @Query("SELECT * from Autor ORDER BY nombre ASC")
    fun getAllAutors(): Flow<List<Autor>>

    @Query("SELECT * from Autor WHERE id = :id")
    fun getAutor(id: Int): Flow<Autor>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entity: Autor)

    @Update()
    suspend fun update(entity: Autor)

    @Delete
    suspend fun delete(entity: Autor)
}