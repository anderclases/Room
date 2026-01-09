package com.example.libreria

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "libro")
data class Libro(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val price: Double,
    val quantity: Int
)