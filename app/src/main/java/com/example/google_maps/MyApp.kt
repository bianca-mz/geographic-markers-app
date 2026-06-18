package com.example.google_maps

import android.app.Application
import com.example.google_maps.core.permissions.supabase.MySupabaseClient

//Se ejecuta al arrancar la app y prepara el entorno
class MyApp : Application() {

    companion object {
        lateinit var database: MySupabaseClient
    }

    override fun onCreate() {
        super.onCreate()
        database = MySupabaseClient(
            supabaseUrl = com.example.google_maps.BuildConfig.SUPABASE_URL,
            supabaseKey = com.example.google_maps.BuildConfig.SUPABASE_KEY
        )
    }
}
