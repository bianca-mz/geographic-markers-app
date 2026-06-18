package com.example.google_maps.features.markers

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.google_maps.MyApp
import kotlinx.coroutines.launch

class AddMarkerViewModel : ViewModel() {

    private val _title = mutableStateOf("")
    val title: State<String> = _title

    private val _description = mutableStateOf("")
    val description: State<String> = _description

    fun editTitle(value: String) {
        _title.value = value
    }

    fun editDescription(value: String) {
        _description.value = value
    }

    fun saveMarker(
        lat: Double,
        lng: Double,
        imageUrl: String? = null,
        onDone: () -> Unit
    ) {
        viewModelScope.launch {
            val marker = Marker(
                title = _title.value.ifBlank { "Sin título" },
                description = _description.value.ifBlank { "Sin descripción" },
                lat = lat,
                lng = lng,
                image_url = imageUrl
            )

            MyApp.database.insertMarker(marker)
            onDone()
        }
    }
}