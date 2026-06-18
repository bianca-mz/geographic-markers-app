package com.example.google_maps.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddLocationAlt
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Map
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.google_maps.ui.navigation.Destination

enum class DrawerItem(
    val icon: ImageVector,
    val text: String,
    val destination: Destination
) {
    MAP(Icons.Default.Map, "Mapa", Destination.MapScreen),
    MARKER_LIST(Icons.Default.List, "Lista de marcadores", Destination.MarkerList),
}