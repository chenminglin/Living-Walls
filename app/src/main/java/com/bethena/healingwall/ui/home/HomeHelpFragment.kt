package com.bethena.healingwall.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.LocaleListCompat
import com.bethena.base_wall.utils.AppUtil
import com.bethena.healingwall.R
import com.bethena.healingwall.utils.LocalUtil
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

        var local = LocaleListCompat.getDefault()[0]

//        var langValues = resources.getStringArray(R.array.settings_language_values)
        assetName = when (local?.language) {
            "zh" -> {
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