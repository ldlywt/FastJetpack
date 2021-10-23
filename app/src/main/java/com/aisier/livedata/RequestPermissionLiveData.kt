package com.aisier.livedata

import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.LiveData

class RequestPermissionLiveData(
    private val registry: ActivityResultRegistry,
    private val key: String
) : LiveData<Boolean>() {

    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

    override fun onActive() {
        requestPermissionLauncher =
            registry.register(key, ActivityResultContracts.RequestPermission()) { result ->
                value = result
            }
    }

    override fun onInactive() = requestPermissionLauncher.unregister()

    fun requestPermission(permission: String) {
        requestPermissionLauncher.launch(permission)
    }

}