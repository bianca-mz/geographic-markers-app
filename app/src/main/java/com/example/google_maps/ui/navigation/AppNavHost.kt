package com.example.google_maps.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.google_maps.features.maps.MapScreen
import com.example.google_maps.features.markers.EditMarkerScreen
import com.example.google_maps.ui.layout.MainScaffold
import com.example.google_maps.ui.screens.AddMarkerScreen
import com.example.google_maps.ui.screens.MarkerListScreen

@Composable
fun AppNavHost(navController: NavHostController) {
    MainScaffold(navController = navController) {

        NavHost(
            navController = navController,
            startDestination = Destination.MapScreen
        ) {
            composable<Destination.MapScreen> {
                MapScreen(navController = navController)
            }

            composable<Destination.MarkerList> {
                MarkerListScreen(
                    navigateToEdit = { markerId ->
                        navController.navigate(Destination.EditMarker(markerId))
                    }
                )
            }

            composable<Destination.AddMarker> { backStackEntry ->
                val addMarker = backStackEntry.toRoute<Destination.AddMarker>()

                AddMarkerScreen(
                    lat = addMarker.lat,
                    lng = addMarker.lng,
                    navigateBack = { navController.popBackStack() }
                )
            }

            composable<Destination.EditMarker> { backStackEntry ->
                val editMarker = backStackEntry.toRoute<Destination.EditMarker>()
                EditMarkerScreen(
                    markerId = editMarker.markerId,
                    navigateBack = { navController.popBackStack() }
                )
            }
        }
    }
}