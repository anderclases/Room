package com.example.libreria
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.libreria.data.dao.LibroDao

@Database(entities = [Libro::class,Autor::class, Categoria::class], version = 1, exportSchema = false)
abstract class LibreriaDatabase : RoomDatabase() {
        abstract fun libroDao(): LibroDao
        abstract fun autorDao(): AutorDao
        abstract fun categoriaDao(): CategoriaDao
    companion object {
        @Volatile
        private var Instance: LibreriaDatabase? = null

        fun getDatabase(context: Context): LibreriaDatabase {
            //context.deleteDatabase("Libreria_database")
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context,
                    LibreriaDatabase::class.java,
                    "Libreria_database"
                )
                    .build()
                    .also { Instance = it }
            }
        }
    }
}