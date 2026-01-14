package com.example.libreria.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.libreria.Autor
import com.example.libreria.AutorDao
import com.example.libreria.ui.shared.AutorViewModel


@Composable
fun LibroForm(
    titulo: String,
    publicacion: String,
    labelVentana: String, // Ejemplo: "Nuevo Libro" o "Editar Libro"
    onTituloChange: (String) -> Unit,
    onAutorChange: (Int) -> Unit,
    onPublicacionChange: (String) -> Unit,
    onAceptarClick: () -> Unit,
    onCancelarClick: () -> Unit,
    autorViewModel: AutorViewModel
) {

    val listaAutores by autorViewModel.todosLosAutores.collectAsState(initial = emptyList())

    DefaultColumn {
        Text(labelVentana)

        DefaultOutlinedTextField(
            texto = titulo,
            onTextoChange = { nuevoTexto -> onTituloChange(nuevoTexto) },
            placeholder = "Título"
        )

        DropdownConBusqueda(
            listaDatos = listaAutores, // List<Autor> que recibes del ViewModel
            idDatoSeleccionado = autorViewModel.idSelector, // el id actual seleccionado, si cambiamos este valor se selecciona un autor
            onDatoSeleccionado = { idSeleccionado ->
                autorViewModel.idSelector = idSeleccionado // actualizas el estado
            },
            idDatoDatabase = {idObjeto ->
                onAutorChange(idObjeto)
            },
            label = "Autor"
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
