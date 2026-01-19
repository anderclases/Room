package com.example.libreria.ui.ventanas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.libreria.MyLog
import com.example.libreria.ui.components.DefaultColumn
import com.example.libreria.ui.components.DefaultOutlinedTextField
import com.example.libreria.ui.shared.LibroViewModel
import kotlinx.coroutines.launch


@Composable
fun LibroForm(
    navController: NavController, modifier: Modifier, viewModel: LibroViewModel,option:String) {


    val uiScope = rememberCoroutineScope()


    DefaultColumn {
        Text("LibroForm: " + option)

        DefaultOutlinedTextField(
            texto = viewModel.titulo,
            onTextoChange = { nuevoTexto -> viewModel.onTituloChanged(nuevoTexto) },
            placeholder = "Título"
        )

        DefaultOutlinedTextField(
            texto = viewModel.autorId,
            onTextoChange = { nuevoTexto -> viewModel.onAutorIdChanged(nuevoTexto) },
            placeholder = "Autor Id"
        )
        DefaultOutlinedTextField(
            texto = viewModel.publicacion,
            onTextoChange = { nuevoTexto -> viewModel.onPublicacionChanged(nuevoTexto) },
            placeholder = "Año de publicacion"
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = {
                navController.navigate("ver")
            }) { Text("Cancelar") }
            Button(onClick = {
                uiScope.launch {
                    if("crear".equals(option)) {
                        MyLog.d("Crear aceptar")
                        viewModel.guardarLibro() {navController.popBackStack()}
                    }
                    if ("editar".equals(option)) {
                        MyLog.d("editar aceptar")
                        viewModel.editarLibro() {navController.popBackStack()}
                    }
                }
            }) { Text("Aceptar") }
        }
        viewModel.errorMensaje?.let { Text(it,color = Color.Red) }
    }
}
