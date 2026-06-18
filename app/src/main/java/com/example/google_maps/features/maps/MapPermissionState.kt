package com.example.google_maps.features.maps

sealed class MapPermissionState {
    object Requesting : MapPermissionState()
    object ShowDenied : MapPermissionState()
    object ShowPermanentlyDenied : MapPermissionState()
    object NavigateToMap : MapPermissionState()
}
