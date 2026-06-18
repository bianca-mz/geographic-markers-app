package com.example.google_maps.core.permissions.supabase

import com.example.google_maps.features.markers.Marker
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.storage.Storage

//clase que crea y mantiee la conexion con supabase
class MySupabaseClient(
    supabaseUrl: String,
    supabaseKey: String
) {

    lateinit var client: SupabaseClient

    init {
        client = createSupabaseClient(
            supabaseUrl = supabaseUrl,
            supabaseKey = supabaseKey
        ) {
            install(Postgrest)
            install(Storage)
        }
    }

    suspend fun getAllMarkers(): List<Marker> {
        return client.from("markers")
            .select()
            .decodeList<Marker>()
    }

    suspend fun insertMarker(marker: Marker) {
        client.from("markers").insert(marker)
    }

    suspend fun deleteMarker(id: Long) {
        client.from("markers").delete {
            filter {
                eq("id", id)
            }
        }
    }

    suspend fun getMarkerById(id: Long): Marker {
        return client.from("markers")
            .select {
                filter {
                    eq("id", id)
                }
            }
            .decodeSingle<Marker>()
    }

    suspend fun updateMarker(id: Long, title: String, description: String) {
        client.from("markers").update({
            set("title", title)
            set("description", description)
        }) {
            filter {
                eq("id", id)
            }
        }
    }

    suspend fun updateMarkerImage(id: Long, imageUrl: String) {
        client.from("markers").update({
            set("image_url", imageUrl)
        }) {
            filter {
                eq("id", id)
            }
        }
    }
}