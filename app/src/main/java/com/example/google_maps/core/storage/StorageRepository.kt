package com.example.google_maps.core.storage

import com.example.google_maps.MyApp
import io.github.jan.supabase.storage.storage
import java.util.UUID

class StorageRepository {

    suspend fun uploadImage(imageBytes: ByteArray): String {
        val fileName = "${System.currentTimeMillis()}_${UUID.randomUUID()}.jpg"
        val bucket = MyApp.database.client.storage.from("marker-images")

        bucket.upload(
            path = fileName,
            data = imageBytes
        )

        return bucket.publicUrl(fileName)
    }

    suspend fun deleteImage(imageName: String) {
        val bucket = MyApp.database.client.storage.from("marker-images")
        bucket.delete(imageName)
    }
}