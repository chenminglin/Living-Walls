package com.bethena.living_walls.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.bethena.base_wall.BaseActivity
import com.bethena.living_walls.R
import com.bethena.living_walls.ui.home.MainActivity

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        var preference = PreferenceManager.getDefaultSharedPreferences(this)
        var themeDefaultValue = getString(R.string.settings_item_theme_defaul)
        var themeKey = getString(R.string.settings_item_theme_key)
        var themeValue = preference.getString(themeKey, themeDefaultValue)
        var themeValues = resources.getStringArray(R.array.settings_theme_values)
        var nightMode = when (themeValue) {
            themeValues[0] -> {
                AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            }
            themeValues[1] -> {
                AppCompatDelegate.MODE_NIGHT_NO
            }
            themeValues[2] -> {
                AppCompatDelegate.MODE_NIGHT_YES
            }
            else -> {
                AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            }
        }
        AppCompatDelegate.setDefaultNightMode(nightMode)

        window.decorView.postDelayed(Runnable {
            if (isFinishing) {
                return@Runnable
            }
            MainActivity.start(this)
            finish()
        }, 300)


    }


}