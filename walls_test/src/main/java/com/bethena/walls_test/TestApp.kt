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


    //校验身份证
    fun isIdCard(idCard: String): Boolean {
        val pattern = "(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])".toRegex()
        return pattern.matches(idCard)
    }

    //校验邮箱
    fun isEmail(email: String): Boolean {
        val pattern = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*".toRegex()
        return pattern.matches(email)
    }

    
    





}