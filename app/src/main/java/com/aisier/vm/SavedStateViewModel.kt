package com.aisier.vm

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class SavedStateViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    companion object {
        const val SAVE_STATE_KEY_STRING = "user_name"
        const val SAVE_STATE_KEY_LIVE_DATE = "input_livedata"
    }

    var userName: String?
        get() = savedStateHandle.get(SAVE_STATE_KEY_STRING)
        set(value) = savedStateHandle.set(SAVE_STATE_KEY_STRING, value)

    val inputLiveData = savedStateHandle.getLiveData<String>(SAVE_STATE_KEY_LIVE_DATE)

}