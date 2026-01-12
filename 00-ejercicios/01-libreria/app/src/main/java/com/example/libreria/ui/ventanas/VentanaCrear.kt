package com.example.libreria.ui.ventanas

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.libreria.MyLog
import com.example.libreria.ui.components.DefaultColumn
import com.example.libreria.ui.components.LibroForm
import com.example.libreria.ui.shared.LibroViewModel

@Composable
fun VentanaCrear(navController: NavController, modifier: Modifier, viewModel: LibroViewModel) {

    val clickGuardarLibro = {
        MyLog.d("clickGuardarLibro, Se va a crear el libro: "+ viewModel.titulo)
        viewModel.guardarLibro {
            navController.popBackStack()
        }
    }

    DefaultColumn(modifier = modifier) {
        Text(text = "VentanaCrear")
        LibroForm(
            labelVentana = "Añadir Nuevo Libro",
            titulo = viewModel.titulo,
            autor = viewModel.autor,
            publicacion = viewModel.publicacion,
            onTituloChange = { viewModel.onTituloChanged(it) },
            onAutorChange = { viewModel.onAutorChanged(it) },
            onPublicacionChange = { viewModel.onPublicacionChanged(it) },
            onAceptarClick = clickGuardarLibro,
            onCancelarClick = { navController.popBackStack() }
        )
    }
}