package com.example.libreria.ui.ventanas

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.libreria.MyLog
import com.example.libreria.ui.components.DefaultColumn
import com.example.libreria.ui.components.LibroForm
import com.example.libreria.ui.shared.AutorViewModel
import com.example.libreria.ui.shared.LibreriaViewModel
import com.example.libreria.ui.shared.LibroViewModel


@Composable
fun VentanaEditarLibro(navController: NavController, modifier: Modifier, libroViewModel: LibroViewModel,autorViewModel: AutorViewModel, id:Int) {
    val clickEditarLibro = {
        MyLog.d("Se va a editar el libro: titulo:${libroViewModel.titulo}, id_autor:${libroViewModel.autor_id}, añoPubli: ${libroViewModel.publicacion}")
        libroViewModel.editarLibro(id) {
            navController.popBackStack()
        }
    }
    LaunchedEffect(id) {
        libroViewModel.observarLibro(id)
    }


    DefaultColumn(modifier = modifier) {
        Text(text = "VentanaEditar")

        LibroForm(
            labelVentana = "Añadir Nuevo Libro",
            titulo = libroViewModel.titulo,
            autor = libroViewModel.autor_id.toString(),
            publicacion = libroViewModel.publicacion,
            onTituloChange = { libroViewModel.onTituloChanged(it) },
            onAutorChange = { autorViewModel.onNombreChanged(it) },
            onPublicacionChange = { libroViewModel.onPublicacionChanged(it) },
            onAceptarClick = clickEditarLibro,
            onCancelarClick = { navController.popBackStack() },
            autorViewModel = autorViewModel
        )
    }
}