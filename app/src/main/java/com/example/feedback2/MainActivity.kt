package com.example.feedback2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NovelaApp()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NovelaApp() {
    val navController = rememberNavController()
    val viewModel: NovelaViewModel = viewModel()
    val isLoading = remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Gestión de Novelas") }
            )
        }
    ) {paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            NavigationHost(
                navController = navController,
                viewModel = viewModel,
                isLoading = isLoading,
                coroutineScope = coroutineScope
            )
        }
    }
}

@Composable
fun NavigationHost(
    navController: NavHostController,
    viewModel: NovelaViewModel,
    isLoading: MutableState<Boolean>,
    coroutineScope: CoroutineScope
) {
    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            PantallaPrincipal(
                novelas = viewModel.novelas,
                onAgregarClick = { navController.navigate("agregar") },
                onEliminarClick = { novela -> viewModel.eliminarNovela(novela) },
                onVerDetallesClick = { novela ->
                    navController.navigate("detalles/${novela.titulo}")
                },
                onCargarNovelas = {
                    isLoading.value = true
                    coroutineScope.launch {
                        viewModel.cargarNovelas()
                        isLoading.value = false
                    }
                },
                isLoading = isLoading.value
            )
        }
        composable("agregar") {
            AgregarNovela { novela ->
                viewModel.agregarNovela(novela)
                navController.popBackStack()
            }
        }
        composable("detalles/{titulo}") { backStackEntry ->
            val titulo = backStackEntry.arguments?.getString("titulo")
            val novela = viewModel.novelas.firstOrNull { it.titulo == titulo }
            novela?.let {
                DetallesNovela(
                    novela = it,
                    onMarcarFavorita = { novela ->
                        viewModel.marcarFavorita(novela)
                    },
                    onVolver = { navController.popBackStack() }
                )
            }
        }
        composable("resenas") {
            PantallaResenas(novelas = viewModel.novelas) { novela ->
                navController.navigate("agregar_resena/${novela.titulo}")
            }
        }
        composable("agregar_resena/{titulo}") { backStackEntry ->
            val titulo = backStackEntry.arguments?.getString("titulo")
            val novela = viewModel.novelas.firstOrNull { it.titulo == titulo }
            novela?.let {
                AgregarResena(novela = it) { resena ->
                    viewModel.agregarResena(novela, resena)
                    navController.popBackStack() //Regresar a la pantalla de reseñas
                }
            }
        }
    }
}
