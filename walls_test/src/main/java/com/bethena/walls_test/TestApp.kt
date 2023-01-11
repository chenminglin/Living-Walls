package com.bethena.walls_test

import android.app.Application
import com.bethena.base_wall.BaseEngineHandler
import com.bethena.walls.starry_sky.StarrySkyEngineHandler

class TestApp: Application() {

    companion object{
        val appContext = lazy {
            this
        }
        var wallEngineHandler: BaseEngineHandler? = null
    }

    override fun onCreate() {
        super.onCreate()
        wallEngineHandler = StarrySkyEngineHandler(this)
    }
}