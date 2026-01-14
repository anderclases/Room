package com.example.libreria.ui.ventanas

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.libreria.MyLog
import com.example.libreria.ui.components.DefaultColumn
import com.example.libreria.ui.components.LibroForm
import com.example.libreria.ui.shared.AutorViewModel
import com.example.libreria.ui.shared.LibroViewModel

@Composable
fun VentanaCrearLibro(navController: NavController, modifier: Modifier, libroViewModel: LibroViewModel,autorViewModel: AutorViewModel) {

    val clickGuardarLibro = {
        MyLog.d("clickGuardarLibro, Se va a crear el libro: "+ libroViewModel.titulo)
        libroViewModel.guardarLibro {
            navController.popBackStack()
        }
    }

    DefaultColumn(modifier = modifier) {
        Text(text = "VentanaCrearLibro")
        LibroForm(
            labelVentana = "Añadir Nuevo Libro",
            titulo = libroViewModel.titulo,
            publicacion = libroViewModel.publicacion,
            onTituloChange = { libroViewModel.onTituloChanged(it) },
            onAutorChange = { libroViewModel.onAutor_idChanged(it) },
            onPublicacionChange = { libroViewModel.onPublicacionChanged(it) },
            onAceptarClick = clickGuardarLibro,
            onCancelarClick = { navController.popBackStack() },
            autorViewModel = autorViewModel
        )
    }
}