package com.example.google_maps.features.markers

import kotlinx.serialization.Serializable

//es la representracion de la tabla Marker de Supabase
@Serializable
data class Marker(
    val id: Long? = null,
    val title: String,
    val description: String? = null,
    val lat: Double,
    val lng: Double,
    val image_url: String? = null,
    val created_at: String? = null
)