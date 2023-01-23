package com.bethena.healing.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bethena.base_wall.utils.AppUtil
import com.bethena.healing.R
import com.bethena.healing.utils.LocalUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class HomeHelpFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home_help, null, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var tv_help = view.findViewById<TextView>(R.id.tv_help)

        var assetName = "help_cn.txt"

        var local = LocalUtil.getLocal(requireContext())
        var langValues = resources.getStringArray(R.array.settings_language_values)
        assetName = when (local) {
            langValues[2] -> {
                "help_cn.txt"
            }
            else -> {
                "help_en.txt"
            }
        }
        var text = AppUtil.getTextFromAsset(requireContext(), assetName)

        tv_help.text = text
    }
}