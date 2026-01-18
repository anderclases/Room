package com.example.libreria.ui.ventanas

import android.widget.Toast
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
import kotlinx.coroutines.CoroutineScope
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
    val libros by viewModel.librosFiltrados.collectAsState()
    val uiScope = rememberCoroutineScope()
    val context = LocalContext.current


    // FUNCIONES ELIMINAR, EDITAR Y CREAR
    fun eliminarLibro(id: Int) {
        MyLog.d("VentanaVer.eliminarLibro")
        uiScope.launch {
            viewModel.eliminarLibro(id)
            Toast.makeText(context,"El libro ha sido eliminado",Toast.LENGTH_SHORT).show()
        }
    }

    fun editarLibro(id: Int) {
        MyLog.d("VentanaVer.editarLibro")
        uiScope.launch {
            viewModel.observarLibro(id)
            navController.navigate("libroForm/editar")
        }
    }

    fun crearLibro() {
        MyLog.d("VentanaVer.crearLibro")
        navController.navigate("libroForm/crear")
    }

    DefaultColumn(modifier = modifier) {
        Text("VentanaVer")

        Button({ viewModel.insertarDatosPrueba() }) { Text("Insertar datos de prueba") }
        // BOTONES
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = {
                crearLibro()
            }) {
                Text("Añadir libro")
            }
            Button(onClick = {
                MyLog.d("Boton aplicarFiltros")
                viewModel.aplicarFiltros(viewModel.filtroTitulo, viewModel.filtroAutor)
            })
            { Text("Aplicar Filtros") }
        }

        // FORMULARIOS
        DefaultOutlinedTextField(
            viewModel.filtroTitulo,
            { nuevoTitulo -> viewModel.onFiltroTituloChanged(nuevoTitulo) },
            "Titulo"
        )

        // COLUMNA DE VISUALICACIÓN DE DATOS
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ) {
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
                        editarLibro(libroSeleccionado.id)
                    },
                    onDeleteClick = { libroSeleccionado ->
                        eliminarLibro(libroSeleccionado.id)
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
            )

            Text(
                text = libro.autor_id.toString(),
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
