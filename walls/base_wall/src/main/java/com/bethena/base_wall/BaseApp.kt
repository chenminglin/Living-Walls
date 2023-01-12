package com.bethena.base_wall

import android.app.Application
import android.content.Context
import kotlin.properties.Delegates

open class BaseApp: Application() {

    companion object {
        var application : Application by Delegates.notNull()
        var appContext: Context by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        application = this
        appContext = applicationContext
    }
}