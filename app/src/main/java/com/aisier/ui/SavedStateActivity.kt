package com.aisier.ui

import android.util.Log
import androidx.activity.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.aisier.R
import com.aisier.architecture.base.BaseActivity
import com.aisier.databinding.ActivitySavedStateBinding
import com.aisier.vm.SavedStateViewModel

class SavedStateActivity : BaseActivity(R.layout.activity_saved_state) {

    private val stateViewModel by viewModels<SavedStateViewModel>()
    private val mBinding by viewBinding(ActivitySavedStateBinding::bind)

    override fun init() {
        Log.i("SavedStateActivity--> ", "SavedStateViewModel: $stateViewModel")
        Log.i("SavedStateActivity--> ", "userName: ${stateViewModel.userName}")
        val value: String = stateViewModel.inputLiveData.value.toString()
        Log.i("SavedStateActivity--> ", "input text: ${value}")

        mBinding.submit.setOnClickListener {
            stateViewModel.userName = "Hello world"
            val inputText: String = mBinding.editText.toString()
            stateViewModel.inputLiveData.value = inputText
        }
    }


}