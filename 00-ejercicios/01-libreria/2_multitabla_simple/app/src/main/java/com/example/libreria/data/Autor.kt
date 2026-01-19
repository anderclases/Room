package com.example.libreria.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "autor")
data class Autor(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String,
    val nacionalidad: String,
    val fechaNacimiento: String
)