package com.bethena.living_walls.ui.settings

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
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
        setPreferencesFromResource(R.xml.settings_preferences, rootKey)

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
        var a = sp.getString(key, "")

        when (key) {
            resources.getString(R.string.settings_item_theme_key) -> {
                var themeDefault = resources.getString(R.string.settings_item_theme_defaul)
                var themeValue = sp.getString(key, themeDefault)
                changeTheme(themeValue!!)
            }
            resources.getString(R.string.settings_item_language_key)->{
                var languageDefault = resources.getString(R.string.settings_item_language_defaul)
                var languageValue = sp.getString(key, languageDefault)
                var local = LocaleListCompat.forLanguageTags(languageValue)
                changeLanguage(local)
            }
        }

    }

    fun changeTheme(value: String) {
        var values = resources.getStringArray(R.array.settings_theme_values)
        var uiMode = when (value) {
            values[0] -> {
                AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            }
            values[1] -> {
                AppCompatDelegate.MODE_NIGHT_NO
            }
            values[2] -> {
                AppCompatDelegate.MODE_NIGHT_YES
            }
            else -> {
                AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            }
        }
        AppCompatDelegate.setDefaultNightMode(uiMode)
    }

    fun changeLanguage(local: LocaleListCompat){
        AppCompatDelegate.setApplicationLocales(local)
    }


}