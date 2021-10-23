package com.aisier.livedata

import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment

inline fun Fragment.requestPermission(
    permission: String,
    crossinline granted: (permission: String) -> Unit = {},
    crossinline denied: (permission: String) -> Unit = {},
    crossinline explained: (permission: String) -> Unit = {}

) {

    registerForActivityResult(ActivityResultContracts.RequestPermission()) { result: Boolean ->
        when {
            result -> granted.invoke(permission)
            shouldShowRequestPermissionRationale(permission) -> denied.invoke(permission)
            else -> explained.invoke(permission)
        }
    }.launch(permission)
}

inline fun AppCompatActivity.requestPermission(
    permission: String,
    crossinline granted: (permission: String) -> Unit = {},
    crossinline denied: (permission: String) -> Unit = {},
    crossinline explained: (permission: String) -> Unit = {}
) {


    registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
        when {
            result -> granted.invoke(permission)
            ActivityCompat.shouldShowRequestPermissionRationale(this, permission) -> denied.invoke(
                permission
            )
            else -> explained.invoke(permission)
        }
    }.launch(permission)
}