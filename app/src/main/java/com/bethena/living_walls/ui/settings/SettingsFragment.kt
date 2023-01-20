package com.bethena.living_walls.ui.settings

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.bethena.base_wall.utils.LogUtil
import com.bethena.living_walls.BuildConfig
import com.bethena.living_walls.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder


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

    override fun onPreferenceTreeClick(preference: Preference): Boolean {
        when (preference.title) {
            getString(R.string.settings_item_about) -> {
                activity?.let {
//                    var aboutFragment = AboutFragment()
//                    aboutFragment.show(it.supportFragmentManager,"")

                    var dialogView = layoutInflater.inflate(R.layout.fragment_about,null,false)
                    var tv_version = dialogView.findViewById<TextView>(R.id.tv_version)
                    tv_version.text = BuildConfig.VERSION_NAME
                    MaterialAlertDialogBuilder(it)
                        .setView(dialogView)
                        .show()
                }
            }
        }

        return super.onPreferenceTreeClick(preference)
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

        when (key) {
            resources.getString(R.string.settings_item_theme_key) -> {
                var themeDefault = resources.getString(R.string.settings_item_theme_defaul)
                var themeValue = sp.getString(key, themeDefault)
                changeTheme(themeValue!!)
            }
            resources.getString(R.string.settings_item_language_key) -> {
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

    fun changeLanguage(local: LocaleListCompat) {
        AppCompatDelegate.setApplicationLocales(local)
    }


}