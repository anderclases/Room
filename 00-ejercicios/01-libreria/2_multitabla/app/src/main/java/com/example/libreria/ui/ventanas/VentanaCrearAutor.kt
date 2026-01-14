package com.example.libreria.ui.ventanas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.libreria.MyLog
import com.example.libreria.ui.components.DefaultColumn
import com.example.libreria.ui.components.DefaultOutlinedTextField
import com.example.libreria.ui.shared.AutorViewModel

@Composable
fun VentanaCrearAutor(navController: NavController, modifier: Modifier, viewModel: AutorViewModel) {

    val clickAceptar = {
        MyLog.d("clickGuardarAutor, Se va a crear el autor: "+ viewModel.nombre)
        viewModel.guardarAutor {
            navController.popBackStack()
        }
    }

    val clickCancelar = {
        navController.navigate("principal")
    }

    DefaultColumn(modifier = modifier) {
        Text(text = "VentanaCrearAutor")
        DefaultColumn {

            DefaultOutlinedTextField(
                texto = viewModel.nombre,
                onTextoChange = { nuevoTexto -> viewModel.onNombreChanged(nuevoTexto) },
                placeholder = "Nombre"
            )

            DefaultOutlinedTextField(
                texto = viewModel.nacionalidad,
                onTextoChange = { nuevoTexto -> viewModel.onNacionalidadChanged(nuevoTexto) },
                placeholder = "Nacionalidad"
            )
            DefaultOutlinedTextField(
                texto = viewModel.fechaNacimiento,
                onTextoChange = { nuevoTexto -> viewModel.onFechaNacimientoChanged(nuevoTexto) },
                placeholder = "Fecha de nacimiento"
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = clickCancelar) { Text("Cancelar") }
                Button(onClick = clickAceptar) { Text("Aceptar") }
            }
        }
    }
}