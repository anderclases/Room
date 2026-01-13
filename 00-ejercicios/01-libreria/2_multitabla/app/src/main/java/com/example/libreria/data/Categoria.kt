package com.example.libreria

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "categoria",
    indices = [Index(value = ["nombre"], unique = true)] // Para que nombre sea UNIQUE
)
data class Categoria(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String,
    val descripcion: String? = null
)