package com.bethena.living_walls.ui.settings

import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.bethena.base_wall.utils.LogUtil
import com.bethena.living_walls.R

class SettingsFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        context?.let {
            PreferenceManager
                .getDefaultSharedPreferences(it).registerOnSharedPreferenceChangeListener(this)

        }

    }

    override fun onDestroy() {
        super.onDestroy()

        context?.let {
            PreferenceManager
                .getDefaultSharedPreferences(it).unregisterOnSharedPreferenceChangeListener(this)

        }
    }

    override fun onSharedPreferenceChanged(sp: SharedPreferences?, key: String?) {
        LogUtil.d("key = $key")
        if (sp == null || key == null) {
            return
        }
        var a = sp.getString(key,"")

    }


}