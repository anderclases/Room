package com.example.libreria.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.libreria.Libro
import com.example.libreria.data.entity.VistaLibroCompleto
import kotlinx.coroutines.flow.Flow

@Dao
interface LibroDao {

    @Query("SELECT * from libro ORDER BY titulo ASC")
    fun getAllLibros(): Flow<List<VistaLibroCompleto>>

    @Query("SELECT * from libro WHERE id = :id")
    fun getLibro(id: Int): Flow<Libro>

    @Insert(onConflict = OnConflictStrategy.Companion.IGNORE)
    suspend fun insert(entity: Libro)

    @Update()
    suspend fun update(entity: Libro)

    @Delete
    suspend fun delete(entity: Libro)


    @Query("""
    SELECT * FROM libro as l
    JOIN autor AS a ON a.id = l.autor_id
    WHERE (:titulo IS NULL OR titulo LIKE '%' || :titulo || '%') 
      AND (:autorName IS NULL OR a.nombre LIKE '%' || :autorName || '%')
""")
    fun filtrarLibros(titulo: String?, autorName: String?): Flow<List<VistaLibroCompleto>>
    // El :titulo IS NULL hace que el filtro es opcional, si el valor es nulo significa que el usuario no quiere aplicar ese filtro en la busqueda.
    // El programa por defecto envia Strings vacios, eso implica mala optimizacion. En caso de strign vacio aplica el filtro aceptando todos los posibles resultados.
}