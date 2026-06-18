package com.example.google_maps.features.maps

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.google_maps.core.permissions.AppPermission
import com.example.google_maps.core.permissions.PermissionContent
import com.example.google_maps.core.permissions.PermissionStatus
import com.example.google_maps.core.permissions.rememberPermissionManager
import com.example.google_maps.ui.navigation.Destination
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.google_maps.features.markers.Marker
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MapProperties

@Composable
fun MapScreen(navController: NavController,
              viewModel: MapViewModel = viewModel()) {

    val permissionManager = rememberPermissionManager(AppPermission.Location)
    val uiState by viewModel.uiState
    val markers by viewModel.markers
    val selectedMarker by viewModel.selectedMarker

    LaunchedEffect(Unit) {
        if (permissionManager.status == PermissionStatus.Unknown) {
            permissionManager.requestPermissions()
        }
    }

    LaunchedEffect(permissionManager.status) {
        viewModel.onPermissionResult(permissionManager.status)
    }

    when (uiState) {

        MapPermissionState.NavigateToMap -> {

            LaunchedEffect(Unit) {
                viewModel.refreshMarkers()
            }

            val itb = LatLng(41.4534225, 2.1837151)

            val cameraPositionState = rememberCameraPositionState {
                position = CameraPosition.fromLatLngZoom(itb, 17f)
            }

            Box(modifier = Modifier.fillMaxSize()) {

                GoogleMap(
                    modifier = Modifier.fillMaxSize(),
                    cameraPositionState = cameraPositionState,
                    onMapLongClick = { latLng ->
                        navController.navigate(
                            Destination.AddMarker(
                                lat = latLng.latitude,
                                lng = latLng.longitude
                            )
                        )
                    },
                    onMapClick = {
                        viewModel.clearSelectedMarker()
                    },
                    uiSettings = MapUiSettings(
                        zoomControlsEnabled = false,
                        compassEnabled = true,
                        mapToolbarEnabled = false,
                        myLocationButtonEnabled = false,
                        rotationGesturesEnabled = true,
                        tiltGesturesEnabled = true
                    ),
                    properties = MapProperties(
                        isMyLocationEnabled = false
                    )
                ) {
                    markers.forEach { marker ->
                        Marker(
                            state = MarkerState(
                                position = LatLng(marker.lat, marker.lng)
                            ),
                            title = marker.title,
                            snippet = marker.description ?: "",
                            onClick = {
                                viewModel.selectMarker(marker)
                                false
                            }
                        )
                    }
                }

                Text(
                    text = "Mantén pulsado el mapa para añadir un marcador",
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 16.dp)
                        .background(
                            MaterialTheme.colorScheme.surface.copy(alpha = 0.9f),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 12.dp, vertical = 6.dp),
                    style = MaterialTheme.typography.bodySmall
                )

                selectedMarker?.let { marker ->
                    MarkerPreviewCard(
                        marker = marker,
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(16.dp)
                    )
                }
            }
        }

        MapPermissionState.ShowDenied -> {
            PermissionContent(
                status = PermissionStatus.Denied,
                onRetry = permissionManager.requestPermissions
            )
        }

        MapPermissionState.ShowPermanentlyDenied -> {
            PermissionContent(
                status = PermissionStatus.PermanentlyDenied,
                onRetry = {}
            )
        }

        MapPermissionState.Requesting -> {
            PermissionContent(
                status = PermissionStatus.Unknown,
                onRetry = permissionManager.requestPermissions
            )
        }
    }
}

@Composable
fun MarkerPreviewCard(
    marker: Marker,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(0.9f)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
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
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(top = 8.dp)
            )

            Text(
                text = marker.description ?: "Sin descripción",
                modifier = Modifier.padding(top = 4.dp)
            )

            Text(
                text = "Lat: ${marker.lat}",
                modifier = Modifier.padding(top = 8.dp)
            )

            Text(
                text = "Lng: ${marker.lng}"
            )
        }
    }
}