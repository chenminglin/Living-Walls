package com.bethena.walls.rainbow

import android.content.Context
import com.bethena.base_wall.BaseWallModule
import com.bethena.base_wall.WallInfo

class RainbowModule : BaseWallModule() {
    override fun init(context: Context): BaseWallModule {
        var name = context.resources.getString(R.string.rainbow_wall_name)
        wallInfo = WallInfo(
            name,
            R.string.rainbow_wall_name,
            "cover_rainbow.png",
            RainbowEngineHandler::class.java.name,
            RainbowConfigFragment::class.java.name,
            BuildConfig.VERSION,
            false,
            3
        )
        return this
    }
}