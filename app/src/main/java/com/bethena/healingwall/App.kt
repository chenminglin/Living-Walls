package com.bethena.healingwall

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.bethena.base_wall.BaseWallModule
import com.bethena.base_wall.utils.SpUtil
import com.bethena.healingwall.utils.LocalUtil
import com.bethena.walls.rainbow.RainbowModule
import com.bethena.walls.starry_sky.StarrySkyModule
import kotlin.properties.Delegates

class App : Application() {
    companion object {
        var spUtil: SpUtil by Delegates.notNull()
        var thiz: App by Delegates.notNull()
        var wallModules = arrayListOf<BaseWallModule>()
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        initTheme()
        initLanguage()
    }

    override fun onCreate() {
        super.onCreate()
        spUtil = SpUtil(this, Const.KEY_APP_SP_NAME)
        thiz = this

        var starrySkyModule = StarrySkyModule().init(this)
        wallModules.add(starrySkyModule)
        var rainbowModule = RainbowModule().init(this)
        wallModules.add(rainbowModule)
    }

    private fun initTheme(){
//        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
//        var config = resources.configuration
//        config.uiMode = Configuration.UI_MODE_NIGHT_YES
//        resources.updateConfiguration(config,resources.displayMetrics)

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
    }


    private fun initLanguage(){
        var localeList = LocalUtil.getLocaleListCompat(this)
        AppCompatDelegate.setApplicationLocales(localeList)
    }


}