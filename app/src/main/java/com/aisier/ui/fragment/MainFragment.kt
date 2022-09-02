package com.aisier.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.navigation.findNavController
import com.aisier.R
import com.aisier.architecture.base.BaseFragment
import com.aisier.ui.SecondActivity

class MainFragment : BaseFragment(R.layout.fragment_main) {
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<View>(R.id.bt_api).setOnClickListener {
            view.findNavController().navigate(R.id.netListFragment)
        }
        view.findViewById<Button>(R.id.bt_save_state).setOnClickListener {
            view.findNavController().navigate(R.id.savedStateFragment)
        }
        view.findViewById<Button>(R.id.bt_change_theme).setOnClickListener {
            requireActivity().startActivity(Intent(requireActivity(), SecondActivity::class.java))
        }
    }
}