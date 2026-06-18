package com.example.google_maps.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.draw.clip
import com.example.google_maps.features.markers.Marker
import com.example.google_maps.features.markers.MarkerListViewModel
import coil.compose.AsyncImage

@Composable
fun MarkerListScreen(
    navigateToEdit: (Long) -> Unit,
    viewModel: MarkerListViewModel = viewModel()
) {
    val markers by viewModel.markers

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(12.dp)
    ) {
        items(markers, key = { it.id ?: 0L }) { marker ->
            val dismissState = rememberSwipeToDismissBoxState()

            if (
                dismissState.currentValue == SwipeToDismissBoxValue.EndToStart &&
                dismissState.targetValue == SwipeToDismissBoxValue.EndToStart
            ) {
                LaunchedEffect(marker.id) {
                    marker.id?.let { viewModel.deleteMarker(it) }
                }
            }

            SwipeToDismissBox(
                state = dismissState,
                backgroundContent = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(vertical = 6.dp)
                            .clip(MaterialTheme.shapes.medium)
                            .background(MaterialTheme.colorScheme.errorContainer)
                            .padding(horizontal = 16.dp),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete"
                        )
                    }
                }
            ) {
                MarkerItem(
                    marker = marker,
                    onClick = {
                        marker.id?.let { navigateToEdit(it) }
                    }
                )
            }
        }
    }
}

@Composable
fun MarkerItem(
    marker: Marker,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (!marker.image_url.isNullOrBlank()) {
                AsyncImage(
                    model = marker.image_url,
                    contentDescription = "Imagen del marcador",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                )
            }

            Text(
                text = marker.title,
                style = MaterialTheme.typography.titleMedium
            )

            Text(text = marker.description ?: "Sin descripción")
            Text(text = "Lat: ${marker.lat}")
            Text(text = "Lng: ${marker.lng}")
        }
    }
}