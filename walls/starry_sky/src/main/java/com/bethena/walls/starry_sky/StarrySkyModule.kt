package com.bethena.walls.starry_sky

import android.content.Context
import com.bethena.base_wall.BaseWallModule
import com.bethena.base_wall.WallInfo

class StarrySkyModule : BaseWallModule() {
    override fun init(context: Context): BaseWallModule {
        var name = context.resources.getString(R.string.starry_sky_wall_name)
        wallInfo = WallInfo(
            name,
            "cover_starry_sky.png",
            StarrySkyEngineHandler::class.java.name,
            StarrySkyConfigFragment::class.java.name
        )
        return this
    }
}