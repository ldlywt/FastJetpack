package com.aisier.livedata

import android.graphics.Bitmap
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.LiveData

class TakePhotoLiveData(private val registry: ActivityResultRegistry, private val key: String) :
    LiveData<Bitmap>() {

    private lateinit var takePhotoLauncher: ActivityResultLauncher<Void?>

    override fun onActive() {
        takePhotoLauncher =
            registry.register(key, ActivityResultContracts.TakePicturePreview()) { result ->
                value = result
            }
    }

    override fun onInactive() = takePhotoLauncher.unregister()

    fun takePhoto() = takePhotoLauncher.launch(null)

}