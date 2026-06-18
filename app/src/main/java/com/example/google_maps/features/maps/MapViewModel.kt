package com.example.google_maps.features.maps

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.google_maps.MyApp
import com.example.google_maps.core.permissions.PermissionStatus
import com.example.google_maps.features.markers.Marker
import kotlinx.coroutines.launch

class MapViewModel : ViewModel() {

    private val _uiState = mutableStateOf<MapPermissionState>(MapPermissionState.Requesting)
    val uiState: State<MapPermissionState> = _uiState

    private val _markers = mutableStateOf<List<Marker>>(emptyList())
    val markers: State<List<Marker>> = _markers

    private val _selectedMarker = mutableStateOf<Marker?>(null)
    val selectedMarker: State<Marker?> = _selectedMarker

    fun selectMarker(marker: Marker) {
        _selectedMarker.value = marker
    }

    fun clearSelectedMarker() {
        _selectedMarker.value = null
    }

    fun onPermissionResult(status: PermissionStatus) {
        _uiState.value = when (status) {
            PermissionStatus.Granted -> {
                loadMarkers()
                MapPermissionState.NavigateToMap
            }
            PermissionStatus.Denied -> MapPermissionState.ShowDenied
            PermissionStatus.PermanentlyDenied -> MapPermissionState.ShowPermanentlyDenied
            PermissionStatus.Unknown -> MapPermissionState.Requesting
        }
    }

    private fun loadMarkers() {
        viewModelScope.launch {
            try {
                val result = MyApp.database.getAllMarkers()
                _markers.value = result
            } catch (e: Exception) {
                Log.e("SUPABASE_TEST", "Error loading markers", e)
            }
        }
    }

    fun addMarker(lat: Double, lng: Double) {
        viewModelScope.launch {
            try {
                val newMarker = Marker(
                    title = "Nuevo marker",
                    description = "Creado desde la app",
                    lat = lat,
                    lng = lng
                )

                MyApp.database.insertMarker(newMarker)
                loadMarkers()
            } catch (e: Exception) {
                Log.e("SUPABASE_TEST", "Error inserting marker", e)
            }
        }
    }

    fun refreshMarkers() {
        loadMarkers()
    }
}