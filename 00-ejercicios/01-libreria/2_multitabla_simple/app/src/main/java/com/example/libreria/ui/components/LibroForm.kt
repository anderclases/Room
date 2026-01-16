package com.example.libreria.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun LibroForm(
    titulo: String,
    autor: String,
    publicacion: String,
    labelVentana: String, // Ejemplo: "Nuevo Libro" o "Editar Libro"
    onTituloChange: (String) -> Unit,
    onAutorChange: (String) -> Unit,
    onPublicacionChange: (String) -> Unit,
    onAceptarClick: () -> Unit,
    onCancelarClick: () -> Unit
) {
    DefaultColumn {
        Text(labelVentana)

        DefaultOutlinedTextField(
            texto = titulo,
            onTextoChange = { nuevoTexto -> onTituloChange(nuevoTexto) },
            placeholder = "Título"
        )

        DefaultOutlinedTextField(
            texto = autor,
            onTextoChange = { nuevoTexto -> onAutorChange(nuevoTexto) },
            placeholder = "Autor"
        )
        DefaultOutlinedTextField(
            texto = publicacion,
            onTextoChange = { nuevoTexto -> onPublicacionChange(nuevoTexto) },
            placeholder = "Año de publicacion"
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = onCancelarClick) { Text("Cancelar") }
            Button(onClick = onAceptarClick) { Text("Aceptar") }
        }
    }
}
