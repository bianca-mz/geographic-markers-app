package com.example.google_maps.features.markers

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.google_maps.MyApp
import kotlinx.coroutines.launch

class MarkerListViewModel : ViewModel() {

    private val _markers = mutableStateOf<List<Marker>>(emptyList())
    val markers: State<List<Marker>> = _markers

    init {
        loadMarkers()
    }

    fun loadMarkers() {
        viewModelScope.launch {
            try {
                val result = MyApp.database.getAllMarkers()
                _markers.value = result
                Log.d("MARKER_LIST", "Markers cargados: $result")
            } catch (e: Exception) {
                Log.e("MARKER_LIST", "Error cargando markers", e)
            }
        }
    }

    fun deleteMarker(id: Long) {
        viewModelScope.launch {
            try {
                MyApp.database.deleteMarker(id)
                loadMarkers()
            } catch (e: Exception) {
                Log.e("MARKER_LIST", "Error borrando marker", e)
            }
        }
    }
}