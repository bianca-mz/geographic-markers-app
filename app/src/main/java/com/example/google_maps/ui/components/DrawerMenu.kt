package com.example.google_maps.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.google_maps.ui.navigation.Destination

//esqueleto de la app

@Composable
fun DrawerMenu(
    currentRoute: String?,
    onNavigate: (Destination) -> Unit
) {
    ModalDrawerSheet {
        Spacer(Modifier.height(16.dp))
        Text(text = "Menú", modifier = Modifier.padding(16.dp))

        DrawerItem.entries.forEach { item ->
            val selected = currentRoute == item.destination.toString()

            NavigationDrawerItem(
                icon = { Icon(imageVector = item.icon, contentDescription = item.text) },
                label = { Text(text = item.text) },
                selected = selected,
                onClick = { onNavigate(item.destination) },
                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
            )
        }
    }
}
