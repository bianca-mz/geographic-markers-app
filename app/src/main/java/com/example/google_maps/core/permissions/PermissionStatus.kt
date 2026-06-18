package com.example.google_maps.core.permissions

//clase para poder definir los estados que podria tene run permiso
sealed class PermissionStatus {
    object Unknown : PermissionStatus()
    object Granted : PermissionStatus()
    object Denied : PermissionStatus()
    object PermanentlyDenied : PermissionStatus()
}
