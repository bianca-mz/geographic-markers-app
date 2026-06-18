package com.example.google_maps.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.google_maps.core.storage.StorageRepository
import com.example.google_maps.features.markers.AddMarkerViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun AddMarkerScreen(
    lat: Double?,
    lng: Double?,
    navigateBack: () -> Unit,
    viewModel: AddMarkerViewModel = viewModel()
) {
    val context = LocalContext.current
    val title by viewModel.title
    val description by viewModel.description

    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var uploadedImageUrl by remember { mutableStateOf<String?>(null) }

    val storageRepository = remember { StorageRepository() }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        selectedImageUri = uri

        if (uri != null) {
            CoroutineScope(Dispatchers.IO).launch {
                val inputStream = context.contentResolver.openInputStream(uri)
                val imageBytes = inputStream?.readBytes()

                if (imageBytes != null) {
                    uploadedImageUrl = storageRepository.uploadImage(imageBytes)
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Añadir marcador",
            modifier = Modifier.padding(bottom = 16.dp)
        )

        TextField(
            value = title,
            onValueChange = { viewModel.editTitle(it) },
            label = { Text("Título") }
        )

        TextField(
            value = description,
            onValueChange = { viewModel.editDescription(it) },
            label = { Text("Descripción") },
            modifier = Modifier.padding(top = 12.dp)
        )

        Text(
            text = "Lat: ${lat ?: "No definida"}",
            modifier = Modifier.padding(top = 16.dp)
        )

        Text(
            text = "Lng: ${lng ?: "No definida"}",
            modifier = Modifier.padding(top = 4.dp)
        )

        Button(
            onClick = {
                imagePickerLauncher.launch("image/*")
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Seleccionar imagen")
        }

        if (selectedImageUri != null) {
            Image(
                painter = rememberAsyncImagePainter(selectedImageUri),
                contentDescription = "Imagen seleccionada",
                modifier = Modifier
                    .padding(top = 16.dp)
                    .size(160.dp)
            )
        }

        Button(
            onClick = {
                if (lat != null && lng != null) {
                    viewModel.saveMarker(
                        lat = lat,
                        lng = lng,
                        imageUrl = uploadedImageUrl
                    ) {
                        navigateBack()
                    }
                }
            },
            modifier = Modifier.padding(top = 20.dp)
        ) {
            Text("Guardar marcador")
        }
    }
}