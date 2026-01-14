package com.example.libreria.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.libreria.Autor
import com.example.libreria.AutorDao
import com.example.libreria.ui.shared.AutorViewModel


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
    onCancelarClick: () -> Unit,
    autorViewModel: AutorViewModel
) {
    val listaAutores = listOf(
        Autor(1, "Gabriel García Márquez", "Colombiana", "06/03/1927"),
        Autor(2, "George Orwell", "Británica", "25/06/1903"),
        Autor(3, "Jane Austen", "Británica", "16/12/1775"),
        Autor(4, "Iria G. Parente", "Española", "29/10/1993")
    )
    DefaultColumn {
        Text(labelVentana)

        DefaultOutlinedTextField(
            texto = titulo,
            onTextoChange = { nuevoTexto -> onTituloChange(nuevoTexto) },
            placeholder = "Título"
        )

        AutorDropdownConBusqueda(
            autores = listaAutores, // List<Autor> que recibes del ViewModel
            autorIdSeleccionado = autor, // el id actual seleccionado
            onAutorSeleccionado = { idSeleccionado ->
                onAutorChange(idSeleccionado) // actualizas el estado
            },
            label = "Autor"
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
