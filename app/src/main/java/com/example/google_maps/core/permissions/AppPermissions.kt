package com.example.google_maps.core.permissions

import android.Manifest

//permisos de la app
sealed class AppPermission(val permissions: List<String>) {
    object Location : AppPermission(
        listOf(Manifest.permission.ACCESS_FINE_LOCATION)
    )
    object CameraAndAudio : AppPermission(
        listOf(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO)
    )
}
