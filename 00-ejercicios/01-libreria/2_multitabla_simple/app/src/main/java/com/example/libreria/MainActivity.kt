package com.example.libreria

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
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
import com.example.libreria.data.LibroRepository
import com.example.libreria.ui.theme.LibreriaTheme
import com.example.libreria.ui.shared.LibroViewModel
import com.example.libreria.ui.ventanas.LibroForm
import com.example.libreria.ui.ventanas.VentanaVer

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LibreriaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    gestorVentanas(Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun gestorVentanas(modifier: Modifier) {
    val context = LocalContext.current

    val repositorio = LibroRepository(LibreriaDatabase.getDatabase(context).libroDao())

    val factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LibroViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return LibroViewModel(repositorio) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    val libroViewModel: LibroViewModel = viewModel(factory = factory)

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "ver") {

        composable(
            route = "libroForm/{option}",
            arguments = listOf(
                navArgument("option") {type = NavType.StringType}
            )
        ) { backStackEntry ->
            val option : String = backStackEntry.arguments?.getString("option") ?: "crear"
            LibroForm(navController, modifier, libroViewModel, option)
        }

        composable("ver") { VentanaVer(navController, modifier, libroViewModel) }
    }

}