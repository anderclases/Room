package com.example.libreria

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "libro",
    foreignKeys = [
        ForeignKey(
            entity = Autor::class,
            parentColumns = ["id"], // PK en tabla autor
            childColumns = ["autor_id"], //FK en tabla libro
            onDelete = ForeignKey.RESTRICT, // Prohibido eliminar autores con libros
            onUpdate = ForeignKey.NO_ACTION
        ),
        ForeignKey(
            entity = Categoria::class,
            parentColumns = ["id"],
            childColumns = ["categoria_id"],
            onDelete = ForeignKey.SET_NULL, // NO RECOMENDADO. En caso de eliminarse los libros que lo tenían pasan a null
            onUpdate = ForeignKey.NO_ACTION
        )
    ],
    indices = [
        Index(value = ["autor_id"], unique = false),
        Index(value = ["categoria_id"], unique = false)
    ]
)
data class Libro(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val titulo: String = "",
    val autor_id: Int,       // (NOT NULL) obligatorio porque es FK
    val categoria_id: Int,   // nullable
    val publicacion: Int = 0
)
