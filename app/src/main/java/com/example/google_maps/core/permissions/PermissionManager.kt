package com.example.google_maps.core.permissions

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

//permisos y acciones que se pueden usar

// Estado que expone el PermissionManager a la UI
// status: estado actual del permiso
// requestPermissions: acción que la UI puede ejecutar para pedirlos
data class PermissionManagerState(
    val status: PermissionStatus,
    val requestPermissions: () -> Unit
)

@Composable
fun rememberPermissionManager(permission: AppPermission): PermissionManagerState {

    val context = LocalContext.current
    val activity = context as? Activity

    // Estado interno del manager (Unknown / Granted / Denied / PermanentlyDenied)
    var status by remember { mutableStateOf<PermissionStatus>(PermissionStatus.Unknown) }

    // Launcher de Compose para pedir varios permisos a la vez y recoger el resultado
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { results ->

            // Si TODOS los permisos han sido concedidos entonces Granted
            val allGranted = results.values.all { it }

            status = when {
                allGranted -> PermissionStatus.Granted

                // Si el usuario denegó pero aún podemos volver a pedirlos entonce Denied
                activity != null && permission.permissions.any { perm ->
                    ActivityCompat.shouldShowRequestPermissionRationale(activity, perm)
                } -> PermissionStatus.Denied

                // Si no hay activity, no podemos decidir bien, lo tratamos como Denied (safe)
                activity == null -> PermissionStatus.Denied

                // Si no se puede volver a pedir (segunda denegación / "Don't ask again") entonces PermanentlyDenied
                else -> PermissionStatus.PermanentlyDenied
            }
        }

    // Acción pública que la UI llamará cuando quiera pedir permisos
    fun requestPermissions() {
        launcher.launch(permission.permissions.toTypedArray())
    }

    // Al entrar, comprobamos si ya estaban concedidos para no pedirlos otra vez
    // (Si cambias el grupo de permisos, se vuelve a comprobar)
    LaunchedEffect(permission) {
        val allGranted = permission.permissions.all { perm ->
            ContextCompat.checkSelfPermission(context, perm) == PackageManager.PERMISSION_GRANTED
        }

        status = if (allGranted) PermissionStatus.Granted else PermissionStatus.Unknown
    }

    return PermissionManagerState(
        status = status,
        requestPermissions = ::requestPermissions
    )
}