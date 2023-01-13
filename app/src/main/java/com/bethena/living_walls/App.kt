package com.bethena.living_walls

import android.app.Application
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

    override fun onCreate() {
        super.onCreate()
        spUtil = SpUtil(this, Const.KEY_APP_SP_NAME)
        thiz = this

        var starrySkyModule = StarrySkyModule().init(this)
        wallModules.add(starrySkyModule)


    }
}