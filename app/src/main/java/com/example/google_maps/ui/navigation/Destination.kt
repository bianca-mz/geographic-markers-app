package com.example.google_maps.ui.navigation

import kotlinx.serialization.Serializable

sealed class Destination {

    @Serializable
    object MapScreen : Destination()

    @Serializable
    object MarkerList : Destination()

    @Serializable
    data class AddMarker(
        val lat: Double? = null,
        val lng: Double? = null
    ) : Destination()

    @Serializable
    data class EditMarker(val markerId: Long) : Destination()
}