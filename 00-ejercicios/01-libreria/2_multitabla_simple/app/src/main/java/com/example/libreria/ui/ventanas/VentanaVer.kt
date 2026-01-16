package com.example.libreria.ui.ventanas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.Hyphens
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.libreria.Libro
import com.example.libreria.MyLog
import com.example.libreria.ui.components.DefaultColumn
import com.example.libreria.ui.components.DefaultOutlinedTextField
import com.example.libreria.ui.shared.LibroViewModel
import kotlinx.coroutines.launch



// Define anchos fijos por columna (ajústalos a tu gusto/maqueta)
private val TITLE_WIDTH = 90.dp
private val AUTHOR_WIDTH = 90.dp
private val YEAR_WIDTH = 70.dp
private val ACTIONS_WIDTH = 96.dp   // suficiente para dos IconButton
private val ROW_HORIZONTAL_PADDING = 8.dp
private val ROW_VERTICAL_PADDING = 12.dp


@Composable
fun VentanaVer(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: LibroViewModel
) {
    //var filtroTitulo by remember  {mutableStateOf("")}
    //var filtroAutor by remember  {mutableStateOf("")}

    //val libros by viewModel.todosLosLibros.collectAsState(initial = emptyList())
    val libros by viewModel.librosFiltrados.collectAsState()
    val uiScope = rememberCoroutineScope()

    var libroAEliminar by remember { mutableStateOf<Libro?>(null) }

    // Tu callback actual, ahora sólo prepara el diálogo:
    val onDeleteClick: (Libro) -> Unit = { libroSeleccionado ->
        libroAEliminar = libroSeleccionado
    }

    // Diálogo de confirmación
    if (libroAEliminar != null) {
        MyLog.d("Se va a eliminar")
        MyLog.d("id libro: ${libroAEliminar!!.id}")
        val libroEliminarId = libroAEliminar!!.id
        AlertDialog(
            onDismissRequest = { libroAEliminar = null },
            title = { Text("Eliminar libro") },
            text = { Text("¿Seguro que deseas eliminar “${libroAEliminar!!.titulo}”? Esta acción no se puede deshacer.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        uiScope.launch {
                            viewModel.eliminarLibro(libroEliminarId)
                        }
                        libroAEliminar = null
                    }
                ) { Text("Eliminar") }
            },
            dismissButton = {
                TextButton(onClick = { libroAEliminar = null }) {
                    Text("Cancelar")
                }
            }
        )
    }


    DefaultColumn(modifier = modifier) {
        Text("VentanaVer")

        Button({ viewModel.insertarDatosPrueba() }) { Text("Insertar datos de prueba") }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = {
                MyLog.d("Boton Crear")
                navController.navigate("crear")
            }) {
                Text("Añadir libro")
            }
            Button(onClick = {
                MyLog.d("Boton aplicarFiltros")
                viewModel.aplicarFiltros(viewModel.filtroTitulo, viewModel.filtroAutor)
            })
            { Text("Aplicar Filtros") }
        }

        DefaultOutlinedTextField(
            viewModel.filtroTitulo,
            { nuevoTitulo -> viewModel.onFiltroTituloChanged(nuevoTitulo) },
            "Titulo"
        )
        DefaultOutlinedTextField(
            viewModel.filtroAutor,
            { nuevoAutor -> viewModel.onFiltroAutorChanged(nuevoAutor) },
            "Autor"
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ) {

            // Encabezado: MISMAS MEDIDAS QUE CADA FILA
            item {
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

            // Carga de filas y funciones eliminar y editar
            items(libros) { libro ->
                LibroItem(
                    libro = libro,
                    onEditClick = { libroSeleccionado ->
                        navController.navigate("editar/${libroSeleccionado.id}")
                    },
                    onDeleteClick = { libroSeleccionado ->
                        libroAEliminar = libroSeleccionado
                    }
                )
            }
        }
    }
}

@Composable
fun LibroItem(
    libro: Libro,
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
                text = libro.titulo,
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
                text = libro.autor,
                modifier = Modifier.width(AUTHOR_WIDTH),
                style = MaterialTheme.typography.bodyLarge.copy(
                    lineBreak = LineBreak.Paragraph,
                    hyphens = Hyphens.Auto
                ),
                softWrap = true,
                textAlign = TextAlign.Center
            )

            Text(
                text = "${libro.published}",
                modifier = Modifier.width(YEAR_WIDTH),
                style = MaterialTheme.typography.bodyLarge,
                softWrap = true,
                textAlign = TextAlign.Center
            )

            Row(
                modifier = Modifier.width(ACTIONS_WIDTH),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = { onEditClick(libro) }) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = "Editar libro"
                    )
                }
                IconButton(onClick = { onDeleteClick(libro) }) {
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
