package com.example.libreria.ui.ventanas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.libreria.Libro
import com.example.libreria.MyLog
import com.example.libreria.ui.components.DefaultColumn
import com.example.libreria.ui.components.DefaultOutlinedTextField
import com.example.libreria.ui.components.LibroItem
import com.example.libreria.ui.components.LibroItemHeader
import com.example.libreria.ui.components.SpeedDialFab
import com.example.libreria.ui.shared.LibreriaViewModel
import kotlinx.coroutines.launch

@Composable
fun VentanaPrincipal(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: LibreriaViewModel
) {
    //var filtroTitulo by remember  {mutableStateOf("")}
    //var filtroAutor by remember  {mutableStateOf("")}

    //val libros by viewModel.todosLosLibros.collectAsState(initial = emptyList())
    val listaLibrosFiltrados by viewModel.librosFiltrados.collectAsState()
    val uiScope = rememberCoroutineScope()

    var libroAEliminar by remember { mutableStateOf<Libro?>(null) }

    // Tu callback actual, ahora sólo prepara el diálogo:
    val onDeleteClick: (Libro) -> Unit = { libroSeleccionado ->
        libroAEliminar = libroSeleccionado
    }


    // Diálogo de confirmación
    if (libroAEliminar != null) {
        MyLog.d("Muestra ventana emergente de eliminar")
        MyLog.d("id libro: ${libroAEliminar!!.id}")
        var idLibroEliminar = libroAEliminar!!.id
        AlertDialog(
            onDismissRequest = { libroAEliminar = null },
            title = { Text("Eliminar libro") },
            text = { Text("¿Seguro que deseas eliminar “${libroAEliminar!!.titulo}”? Esta acción no se puede deshacer.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        uiScope.launch {
                            MyLog.d("Se va pulsado aceptar en la ventana emergente de eliminacion")
                            //viewModel.eliminarLibro(libroAEliminar!!) // puede dar null pointer por culpa de la recomposicion de la variable
                            viewModel.eliminarLibroConId(idLibroEliminar)
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
        Text("VentanaPrincipal")

        Button({ viewModel.insertarDatosPrueba() }) { Text("Insert pruebas") }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = {
                MyLog.d("Boton crear libro")
                navController.navigate("crearLibro")
            }) {
                Text("Añadir libro")
            }
            Button(onClick = {
                MyLog.d("Boton crear autor")
                navController.navigate("crearAutor")
            }) {
                Text("Añadir autor")
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

        // Tabla de items de libros
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ) {
            item {
                LibroItemHeader()
            }
            // Carga de filas y funciones eliminar y editar
            items(listaLibrosFiltrados) { vista ->
                LibroItem(
                    vista = vista,
                    onEditClick = { libroSeleccionado ->
                        navController.navigate("editarLibro/${libroSeleccionado.id}")
                        MyLog.d("Boton editarLibro: ${libroSeleccionado.id},${libroSeleccionado.titulo}")
                    },
                    onDeleteClick = { libroSeleccionado ->
                        libroAEliminar = libroSeleccionado
                        MyLog.d("Boton eliminarLibro: ${libroSeleccionado.id},${libroSeleccionado.titulo}")
                    }
                )
            }
        }
    }
    SpeedDialFab(
        opcion1 = "Libro", onAccion1 = {navController.navigate("crearLibro")},
        opcion2 = "Autor", onAccion2 = {navController.navigate("crearAutor")})
}

