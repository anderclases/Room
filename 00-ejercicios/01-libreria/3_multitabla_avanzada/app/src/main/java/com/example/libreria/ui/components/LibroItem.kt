package com.example.libreria.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.Hyphens
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.libreria.Libro
import com.example.libreria.data.entity.VistaLibroCompleto

// Define anchos fijos por columna (ajústalos a tu gusto/maqueta)
private val TITLE_WIDTH = 90.dp
private val AUTHOR_WIDTH = 90.dp
private val YEAR_WIDTH = 70.dp
private val ACTIONS_WIDTH = 96.dp   // suficiente para dos IconButton
private val ROW_HORIZONTAL_PADDING = 8.dp
private val ROW_VERTICAL_PADDING = 12.dp

@Composable
fun LibroItemHeader() {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = ROW_HORIZONTAL_PADDING,
                vertical = ROW_VERTICAL_PADDING
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Título",
            modifier = Modifier.width(TITLE_WIDTH),
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )
        Text(
            text = "Autor",
            modifier = Modifier.width(AUTHOR_WIDTH),
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )
        Text(
            text = "Año",
            modifier = Modifier.width(YEAR_WIDTH),
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )
        // Acciones: reservar ancho fijo para que no empuje columnas
        Row(
            modifier = Modifier.width(ACTIONS_WIDTH),
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = "Acciones",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }

}

@Composable
fun LibroItem(
    vista: VistaLibroCompleto,
    onEditClick: (Libro) -> Unit,
    onDeleteClick: (Libro) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = ROW_HORIZONTAL_PADDING, vertical = ROW_VERTICAL_PADDING),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = vista.libro.titulo,
                modifier = Modifier.width(TITLE_WIDTH),
                style = MaterialTheme.typography.bodyLarge.copy(
                    lineBreak = LineBreak.Paragraph,
                    hyphens = Hyphens.Auto
                ),
                softWrap = true,
                textAlign = TextAlign.Center
                // Si quieres limitar líneas, usa un número >1:
                // maxLines = 2, overflow = TextOverflow.Ellipsis
            )

            Text(
                text = vista.autor.nombre,
                modifier = Modifier.width(AUTHOR_WIDTH),
                style = MaterialTheme.typography.bodyLarge.copy(
                    lineBreak = LineBreak.Paragraph,
                    hyphens = Hyphens.Auto
                ),
                softWrap = true,
                textAlign = TextAlign.Center
            )

            Text(
                text = "${vista.libro.publicacion}",
                modifier = Modifier.width(YEAR_WIDTH),
                style = MaterialTheme.typography.bodyLarge,
                softWrap = true,
                textAlign = TextAlign.Center
            )

            Row(
                modifier = Modifier.width(ACTIONS_WIDTH),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = { onEditClick(vista.libro) }) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = "Editar libro"
                    )
                }
                IconButton(onClick = { onDeleteClick(vista.libro) }) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "Eliminar libro",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}
