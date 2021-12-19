package com.aisier.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.aisier.R
import com.aisier.vm.SavedStateViewModel

class SavedStateFragment : Fragment(R.layout.fragment_saved_state) {

    private val stateViewModel by viewModels<SavedStateViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i("SavedStateActivity--> ", "SavedStateViewModel: $stateViewModel")
        Log.i("SavedStateActivity--> ", "userName: ${stateViewModel.userName}")
        val value: String = stateViewModel.inputLiveData.value.toString()
        Log.i("SavedStateActivity--> ", "input text: ${value}")

        val submit = view.findViewById<Button>(R.id.submit)
        val editText = view.findViewById<EditText>(R.id.edit_text)

        submit.setOnClickListener {
            stateViewModel.userName = "Hello world"
            val inputText: String = editText.toString()
            stateViewModel.inputLiveData.value = inputText
        }
    }
}