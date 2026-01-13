package com.example.libreria

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(tableName = "autor")
data class Autor(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String,
    val nacionalidad: String,
    val fechaNacimiento: String
)

@Entity(tableName = "categoria")
data class Categoria(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String
)

@Entity(
    tableName = "libro",
    foreignKeys = [
        ForeignKey(
            entity = Autor::class,
            parentColumns = ["id"], // PK en tabla autor
            childColumns = ["autorId"], //FK en tabla libro
            onDelete = ForeignKey.RESTRICT, // Prohibido eliminar autores con libros
            onUpdate = ForeignKey.NO_ACTION
        ),
        ForeignKey(
            entity = Categoria::class,
            parentColumns = ["id"],
            childColumns = ["categoriaId"],
            onDelete = ForeignKey.SET_NULL, // NO RECOMENDADO. En caso de eliminarse los libros que lo tenían pasan a null
            onUpdate = ForeignKey.NO_ACTION
        )
    ],
    indices = [
        Index(value = ["autorId"], unique = false),
        Index(value = ["categoriaId"], unique = false)
    ]
)
data class Libro(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val titulo: String = "",
    val autorId: Int,       // (NOT NULL) obligatorio porque es FK
    val categoriaId: Int,   // nullable
    val publicacion: Int = 0
)
