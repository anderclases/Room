package com.example.libreria.ui.ventanas

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.libreria.MyLog
import com.example.libreria.ui.components.DefaultColumn
import com.example.libreria.ui.components.LibroForm
import com.example.libreria.ui.shared.LibroViewModel


@Composable
fun VentanaEditar(navController: NavController,modifier: Modifier, viewModel: LibroViewModel,id:Int) {
    val clickEditarLibro = {
        MyLog.d("Se va a editar el libro: ${viewModel.titulo},${viewModel.autor},${viewModel.publicacion}")
        viewModel.editarLibro(id) {
            navController.popBackStack()
        }
    }
    LaunchedEffect(id) {
        viewModel.observarLibro(id)
    }


    DefaultColumn(modifier = modifier) {
        Text(text = "VentanaEditar")

        LibroForm(
            labelVentana = "Añadir Nuevo Libro",
            titulo = viewModel.titulo,
            autor = viewModel.autor,
            publicacion = viewModel.publicacion,
            onTituloChange = { viewModel.onTituloChanged(it) },
            onAutorChange = { viewModel.onAutorChanged(it) },
            onPublicacionChange = { viewModel.onPublicacionChanged(it) },
            onAceptarClick = clickEditarLibro,
            onCancelarClick = { navController.popBackStack() }
        )
    }
}