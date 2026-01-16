package com.example.libreria

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.libreria.data.LibreriaRepository
import com.example.libreria.ui.shared.AutorViewModel
import com.example.libreria.ui.shared.LibreriaViewModel
import com.example.libreria.ui.shared.LibroViewModel
import com.example.libreria.ui.ventanas.VentanaCrearAutor
import com.example.libreria.ui.ventanas.VentanaCrearLibro
import com.example.libreria.ui.ventanas.VentanaEditarLibro
import com.example.libreria.ui.ventanas.VentanaPrincipal

@Composable
fun GestorVentanas(modifier: Modifier) {
    val context = LocalContext.current

    val repositorio = LibreriaRepository(
        LibreriaDatabase.getDatabase(context).libroDao(),
        LibreriaDatabase.getDatabase(context).autorDao(),
        LibreriaDatabase.getDatabase(context).categoriaDao()
        )


    class MultiViewModelFactory(
        private val creators: Map<Class<out ViewModel>, () -> ViewModel>
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            val creator = creators[modelClass] ?: creators.entries.firstOrNull {
                modelClass.isAssignableFrom(it.key)
            }?.value ?: throw IllegalArgumentException("Error en GestorVentanas. Unknown ViewModel class: ${modelClass.name}")
            return creator() as T
        }
    }


    val factory = MultiViewModelFactory(
        mapOf(
            LibreriaViewModel::class.java to { LibreriaViewModel(repositorio) },
            LibroViewModel::class.java to { LibroViewModel(repositorio) },
            AutorViewModel::class.java to { AutorViewModel(repositorio) }
        )
    )



    val libreriaViewModel: LibreriaViewModel = viewModel(factory = factory)
    val libroViewModel: LibroViewModel = viewModel(factory = factory)
    val autorViewModel: AutorViewModel = viewModel(factory = factory)

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "principal") {

        composable(
            route = "editarLibro/{id}",
            arguments = listOf(
                navArgument("id") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: 0
            VentanaEditarLibro(navController, modifier, libroViewModel,autorViewModel, id)
        }

        composable("principal") { VentanaPrincipal(navController, modifier, libreriaViewModel) }
        composable("crearLibro") { VentanaCrearLibro(navController, modifier, libroViewModel,autorViewModel) }
        composable("crearAutor") { VentanaCrearAutor(navController, modifier, autorViewModel) }
    }
}