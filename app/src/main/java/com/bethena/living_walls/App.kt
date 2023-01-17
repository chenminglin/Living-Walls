package com.bethena.living_walls

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import com.bethena.base_wall.BaseWallModule
import com.bethena.base_wall.utils.SpUtil
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
    }

    override fun onCreate() {
        super.onCreate()
        spUtil = SpUtil(this, Const.KEY_APP_SP_NAME)
        thiz = this

        var starrySkyModule = StarrySkyModule().init(this)
        wallModules.add(starrySkyModule)


    }

    fun initTheme(){
        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
//        var config = resources.configuration
//        config.uiMode = Configuration.UI_MODE_NIGHT_YES
//        resources.updateConfiguration(config,resources.displayMetrics)
    }
}