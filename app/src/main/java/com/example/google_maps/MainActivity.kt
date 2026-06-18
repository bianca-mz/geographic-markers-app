package com.example.google_maps

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.google_maps.ui.navigation.AppNavHost
import com.example.google_maps.ui.theme.Google_MapsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Google_MapsTheme {
                val navController = rememberNavController()
                AppNavHost(navController = navController)
            }
        }
    }
}

