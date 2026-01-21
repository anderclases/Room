package com.example.libreria.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface LibroDao {

    @Query("SELECT * from libro ORDER BY titulo ASC")
    fun getAllLibros(): Flow<List<Libro>>

    @Query("SELECT * from libro WHERE id = :id")
    fun getLibro(id: Int): Flow<Libro>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entity: Libro)

    @Update()
    suspend fun update(entity: Libro)

    @Delete
    suspend fun delete(entity: Libro)


    @Query("""
    SELECT * FROM libro
    WHERE (:titulo IS NULL OR titulo LIKE '%' || :titulo || '%')
""")
    fun filtrarLibros(titulo: String?): Flow<List<Libro>>

}