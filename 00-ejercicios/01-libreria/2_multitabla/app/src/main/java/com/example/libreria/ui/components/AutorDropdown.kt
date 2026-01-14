package com.example.libreria.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import com.example.libreria.Autor

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.toSize
import com.example.libreria.MyLog
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull


suspend fun <T> Flow<List<T>>.flowToSimpleList(): List<T> {
    return this.firstOrNull() ?: emptyList()
}


@Composable
fun AutorDropdownConBusqueda(
    autores: List<Autor>,
    autorIdSeleccionado: String?,
    onAutorSeleccionado: (String) -> Unit,
    label: String = "Autor"
) {
    var expanded by remember { mutableStateOf(false) }
    var query by remember { mutableStateOf("") }
    var textFieldSize by remember { mutableStateOf(Size.Zero) }
    val density = LocalDensity.current

    val autorNombre = autores.firstOrNull { it.id.toString() == autorIdSeleccionado }?.nombre ?: ""

    // Filtrado simple (case-insensitive)
    val filtrados = remember(query, autores) {
        val q = query.trim().lowercase()
        if (q.isEmpty()) autores else autores.filter { it.nombre.lowercase().contains(q) }
    }

    Box(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = autorNombre,
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            trailingIcon = {
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Abrir menú")
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { textFieldSize = it.size.toSize() }
                .clickable { expanded = true }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.width(with(density) { textFieldSize.width.toDp() })
        ) {
            // Barra de búsqueda dentro del menú
            DropdownMenuItem(
                text = {
                    OutlinedTextField(
                        value = query,
                        onValueChange = { query = it },
                        placeholder = { Text("Buscar autor…") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                onClick = {}, // sin acción, solo UI
                enabled = false // para que no cierre el menú al tocar
            )

            if (filtrados.isEmpty()) {
                DropdownMenuItem(
                    text = { Text("Sin resultados") },
                    onClick = {},
                    enabled = false
                )
            } else {
                filtrados.forEach { autor ->
                    DropdownMenuItem(
                        text = { Text(autor.nombre) },
                        onClick = {
                            MyLog.d("onAutorSeleccionado: ${autor.nombre}")
                            onAutorSeleccionado(autor.nombre)
                            expanded = false
                            query = "" // limpia la búsqueda
                        }
                    )
                }
            }
        }
    }
}

