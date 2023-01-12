package com.bethena.living_walls

import android.app.Application
import com.bethena.base_wall.utils.SpUtil
import kotlin.properties.Delegates

class App : Application() {
    companion object {
        var spUtil : SpUtil by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        spUtil = SpUtil(this,Const.KEY_APP_SP_NAME)
    }
}