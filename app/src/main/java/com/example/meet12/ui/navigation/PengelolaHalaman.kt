package com.example.meet12.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.meet12.ui.view.DestinasiDetail
import com.example.meet12.ui.view.DestinasiEdit
//import com.example.meet12.ui.view.DestinasiDetail
import com.example.meet12.ui.view.DestinasiEntry
import com.example.meet12.ui.view.DestinasiHome

import com.example.meet12.ui.view.DetailView
//import com.example.meet12.ui.view.DestinasiUpdate
//import com.example.meet12.ui.view.DetailScreen
import com.example.meet12.ui.view.EntryMhsScreen
import com.example.meet12.ui.view.HomeScreen
import com.example.meet12.ui.view.UpdateScreen
//import com.example.meet12.ui.view.UpdateScreen
import com.example.meet12.ui.viewmodel.PenyediaViewModel

@Composable
fun PengelolaHalaman(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHome.route,
        modifier = Modifier
    ) {
        composable(DestinasiHome.route) {
            HomeScreen(
                navigateToItemEntry = {
                    navController.navigate(DestinasiEntry.route)
                },
                onDetailClick = { nim ->
                    navController.navigate("${DestinasiDetail.route}/$nim")
                }
            )
        }

        composable(DestinasiEntry.route) {
            EntryMhsScreen(
                navigateBack = {
                    navController.navigate(DestinasiHome.route) {
                        popUpTo(DestinasiHome.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(
            route = "${DestinasiDetail.route}/{nim}",
            arguments = listOf(navArgument("nim") { type = NavType.StringType })
        ) {
            DetailView(
                navigateBack = {
                    navController.navigate(DestinasiHome.route) {
                        popUpTo(DestinasiHome.route) {
                            inclusive = true
                        }
                    }
                },
                navigateToEdit = { nim ->
                    navController.navigate("${DestinasiEdit.route}/$nim")
                }
            )
        }
        // Menyediakan halaman detail berdasarkan 'nim'
        composable(
            route = "${DestinasiEdit.route}/{nim}",
            arguments = listOf(navArgument("nim") { type = NavType.StringType })
        ) {
            UpdateScreen( // Memanggil komponen UpdateScreen (halaman untuk mengedit data)
                navigateBack = {
                    navController.navigate(DestinasiHome.route) {
                        popUpTo(DestinasiHome.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}