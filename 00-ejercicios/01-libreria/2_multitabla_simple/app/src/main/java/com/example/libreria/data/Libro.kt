package com.example.libreria.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(
    tableName = "libro",
    foreignKeys = [
        ForeignKey(
            entity = Autor::class,
            parentColumns = ["id"], // PK en tabla autor
            childColumns = ["autor_id"], //FK en tabla libro
            onDelete = ForeignKey.RESTRICT, // Prohibido eliminar autores con libros
            onUpdate = ForeignKey.NO_ACTION
        )
    ],
    indices = [
        Index(value = ["autor_id"], unique = false),
    ]
)
data class Libro(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val titulo: String = "",
    val autor_id: Int = 0,
    val published: Int = 0
)