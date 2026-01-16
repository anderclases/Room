package com.example.libreria.data.entity

import androidx.room.Embedded
import androidx.room.Relation
import com.example.libreria.Autor
import com.example.libreria.Categoria
import com.example.libreria.Libro

data class VistaLibroCompleto(
    @Embedded val libro: Libro,
    @Relation(
        parentColumn = "autor_id",
        entityColumn = "id"
    )
    val autor: Autor,
    @Relation(
        parentColumn = "categoria_id",
        entityColumn = "id"
    )
    val categoria: Categoria,
)
