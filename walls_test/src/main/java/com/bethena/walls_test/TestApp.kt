package com.bethena.walls_test

import android.app.Application
import com.bethena.base_wall.BaseEngineHandler
import com.bethena.walls.fireworks.FireworksEngineHandler
import com.bethena.walls.rainbow.RainbowEngineHandler
import com.bethena.walls.space.SpaceEngineHandler
import com.bethena.walls.starry_sky.StarrySkyEngineHandler
import com.bethena.walls.thunder_breath.ThunderBreathEngineHandler

class TestApp: Application() {

    companion object{
        val appContext = lazy {
            this
        }
        var wallEngineHandler :BaseEngineHandler? = null
    }

    override fun onCreate() {
        super.onCreate()
//       wallEngineHandler = SpaceEngineHandler(this)
//       wallEngineHandler = RainbowEngineHandler(this)
//       wallEngineHandler = ThunderBreathEngineHandler(this)
//       wallEngineHandler = StarrySkyEngineHandler(this)
        wallEngineHandler = FireworksEngineHandler(this)
    }
}