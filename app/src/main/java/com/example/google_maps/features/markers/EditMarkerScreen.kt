package com.example.google_maps.features.markers

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.google_maps.core.storage.StorageRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.compose.runtime.setValue

@Composable
fun EditMarkerScreen(
    markerId: Long,
    navigateBack: () -> Unit,
    viewModel: EditMarkerViewModel = viewModel()
) {
    val context = LocalContext.current
    val title by viewModel.title
    val description by viewModel.description
    val imageUrl by viewModel.imageUrl

    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val storageRepository = remember { StorageRepository() }

    LaunchedEffect(markerId) {
        viewModel.loadMarker(markerId)
    }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        selectedImageUri = uri

        if (uri != null) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val inputStream = context.contentResolver.openInputStream(uri)
                    val imageBytes = inputStream?.readBytes()

                    if (imageBytes != null) {
                        val uploadedUrl = storageRepository.uploadImage(imageBytes)
                        viewModel.setImageUrl(uploadedUrl)
                    }
                } catch (_: Exception) {
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

        if (!imageUrl.isNullOrBlank()) {
            AsyncImage(
                model = imageUrl,
                contentDescription = "Imagen actual",
                modifier = Modifier
                    .padding(top = 16.dp)
                    .size(180.dp)
            )
        }

        if (selectedImageUri != null) {
            Image(
                painter = rememberAsyncImagePainter(selectedImageUri),
                contentDescription = "Nueva imagen seleccionada",
                modifier = Modifier
                    .padding(top = 16.dp)
                    .size(180.dp)
            )
        }

        Button(
            onClick = {
                imagePickerLauncher.launch("image/*")
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Cambiar imagen")
        }

        Button(
            onClick = {
                viewModel.updateMarker(markerId) {
                    navigateBack()
                }
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Guardar cambios")
        }
    }
}