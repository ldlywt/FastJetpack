package com.aisier.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.navigation.findNavController
import com.aisier.R
import com.aisier.architecture.base.BaseFragment
import io.multimoon.colorful.Colorful
import io.multimoon.colorful.ThemeColor

class MainFragment : BaseFragment(R.layout.fragment_main) {
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.bt_api).setOnClickListener {
            view.findNavController().navigate(R.id.netListFragment)
        }
        view.findViewById<Button>(R.id.bt_save_state).setOnClickListener {
            view.findNavController().navigate(R.id.savedStateFragment)
        }
        view.findViewById<Button>(R.id.bt_change_theme).setOnClickListener {
            Colorful().edit()
                .setPrimaryColor(ThemeColor.values().random())
                .setAccentColor(ThemeColor.values().random())
                .setDarkTheme(false)
                .apply(requireActivity()) {
                    requireActivity().recreate()
                }
        }
    }
}