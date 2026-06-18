package com.example.google_maps.features.markers

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.google_maps.MyApp
import kotlinx.coroutines.launch

class EditMarkerViewModel : ViewModel() {

    private val _title = mutableStateOf("")
    val title: State<String> = _title

    private val _description = mutableStateOf("")
    val description: State<String> = _description

    private val _imageUrl = mutableStateOf<String?>(null)
    val imageUrl: State<String?> = _imageUrl

    fun editTitle(value: String) {
        _title.value = value
    }

    fun editDescription(value: String) {
        _description.value = value
    }

    fun setImageUrl(value: String?) {
        _imageUrl.value = value
    }

    fun loadMarker(markerId: Long) {
        viewModelScope.launch {
            val marker = MyApp.database.getMarkerById(markerId)
            _title.value = marker.title
            _description.value = marker.description ?: ""
            _imageUrl.value = marker.image_url
        }
    }

    fun updateMarker(markerId: Long, onDone: () -> Unit) {
        viewModelScope.launch {
            MyApp.database.updateMarker(
                id = markerId,
                title = _title.value,
                description = _description.value
            )

            _imageUrl.value?.let {
                MyApp.database.updateMarkerImage(markerId, it)
            }

            onDone()
        }
    }
}