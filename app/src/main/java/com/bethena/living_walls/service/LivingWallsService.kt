package com.bethena.living_walls.service

import android.app.Activity
import android.app.WallpaperManager
import android.content.ComponentName
import android.content.Intent
import android.service.wallpaper.WallpaperService
import android.view.SurfaceHolder
import com.bethena.base_wall.BaseEngineHandler
import com.bethena.base_wall.WallInfo
import com.bethena.base_wall.utils.LogUtil
import com.bethena.living_walls.App
import com.bethena.living_walls.Const
import com.bethena.walls.starry_sky.StarrySkyEngineHandler

class LivingWallsService : WallpaperService() {

    override fun onCreate() {
        super.onCreate()
        LogUtil.d("LivingWallsService onCreate-----${this}")
    }

    override fun onCreateEngine(): Engine {
        LogUtil.d("LivingWallsService onCreateEngine-----${this}")
        return LivingWallsEngine()
    }


    inner class LivingWallsEngine : Engine() {

        var engineHandler: BaseEngineHandler? = null


        override fun onCreate(surfaceHolder: SurfaceHolder?) {
            super.onCreate(surfaceHolder)
            engineHandler = StarrySkyEngineHandler(this@LivingWallsService)
            engineHandler?.surfaceCreated(surfaceHolder)
            LogUtil.d("LivingWallsService LivingWallsEngine onCreate-----${this@LivingWallsService} $this")
        }

        override fun onVisibilityChanged(visible: Boolean) {
            super.onVisibilityChanged(visible)
            LogUtil.d("LivingWallsService LivingWallsEngine onVisibilityChanged-----$visible -- ${this@LivingWallsService} $this")
            engineHandler?.onVisibilityChanged(visible)
        }

        override fun onDestroy() {
            super.onDestroy()
            LogUtil.d("LivingWallsService LivingWallsEngine onDestroy-----${this@LivingWallsService} $this")
            engineHandler?.onDestroy()
        }


        override fun onSurfaceCreated(holder: SurfaceHolder?) {
            super.onSurfaceCreated(holder)
            LogUtil.d("LivingWallsService LivingWallsEngine onSurfaceCreated-----${this@LivingWallsService}")
//            engineHandler?.onVisibilityChanged(visible)
        }

        override fun onSurfaceChanged(
            holder: SurfaceHolder?, format: Int, width: Int, height: Int
        ) {
            super.onSurfaceChanged(holder, format, width, height)
            engineHandler?.restart()
            LogUtil.d("LivingWallsService LivingWallsEngine onSurfaceChanged-----${this@LivingWallsService}")
        }

        override fun onSurfaceDestroyed(holder: SurfaceHolder?) {
            super.onSurfaceDestroyed(holder)

            LogUtil.d("LivingWallsService LivingWallsEngine onSurfaceDestroyed-----${this@LivingWallsService}")
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtil.d("LivingWallsService onDestroy-----${this@LivingWallsService}")
    }

    companion object {
        fun start(activity: Activity, wallInfo: WallInfo) {
            val intent: Intent = Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER).putExtra(
                WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
                ComponentName(activity, LivingWallsService::class.java)
            )
            activity.startActivity(intent)
            App.spUtil.putString(Const.KEY_WALLS_NAME, wallInfo.handlerClassName)
            App.spUtil.putString(Const.KEY_WALLS_NAME, wallInfo.handlerClassName)
        }
    }


}