package com.example.libreria

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "libro",
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