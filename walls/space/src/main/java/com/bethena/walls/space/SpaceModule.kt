package com.bethena.walls.space

import android.content.Context
import com.bethena.base_wall.BaseWallModule
import com.bethena.base_wall.WallInfo

class SpaceModule : BaseWallModule() {
    override fun init(context: Context): BaseWallModule {
        var name = context.resources.getString(R.string.space_wall_name)
        wallInfo = WallInfo(
            name,
            R.string.space_wall_name,
            "cover_space.png",
            SpaceEngineHandler::class.java.name,
            SpaceConfigFragment::class.java.name,
            BuildConfig.VERSION,
            false,
            4
        )
        return this
    }
}