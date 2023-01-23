package com.bethena.healingwall.service

import android.app.Activity
import android.app.WallpaperManager
import android.content.ComponentName
import android.content.Intent
import android.service.wallpaper.WallpaperService
import android.view.SurfaceHolder
import com.bethena.base_wall.BaseEngineHandler
import com.bethena.base_wall.WallInfo
import com.bethena.base_wall.utils.LogUtil
import com.bethena.healingwall.App
import com.bethena.healingwall.Const
import com.bethena.walls.starry_sky.StarrySkyEngineHandler

class HealingWallService : WallpaperService() {

    override fun onCreate() {
        super.onCreate()
        LogUtil.d("HealingWallService onCreate-----${this}")
    }

    override fun onCreateEngine(): Engine {
        LogUtil.d("HealingWallService onCreateEngine-----${this}")
        return HealingWallEngine()
    }


    inner class HealingWallEngine : Engine() {

        var engineHandler: BaseEngineHandler? = null


        override fun onCreate(surfaceHolder: SurfaceHolder?) {
            super.onCreate(surfaceHolder)
            engineHandler = StarrySkyEngineHandler(this@HealingWallService)
            engineHandler?.surfaceCreated(surfaceHolder)
            LogUtil.d("HealingWallService HealingWallEngine onCreate-----${this@HealingWallService} $this")
        }

        override fun onVisibilityChanged(visible: Boolean) {
            super.onVisibilityChanged(visible)
            LogUtil.d("HealingWallService HealingWallEngine onVisibilityChanged-----$visible -- ${this@HealingWallService} $this")
            engineHandler?.onVisibilityChanged(visible)
        }

        override fun onDestroy() {
            super.onDestroy()
            LogUtil.d("HealingWallService HealingWallEngine onDestroy-----${this@HealingWallService} $this")
            engineHandler?.onDestroy()
        }


        override fun onSurfaceCreated(holder: SurfaceHolder?) {
            super.onSurfaceCreated(holder)
            LogUtil.d("HealingWallService HealingWallEngine onSurfaceCreated-----${this@HealingWallService}")
//            engineHandler?.onVisibilityChanged(visible)
        }

        override fun onSurfaceChanged(
            holder: SurfaceHolder?, format: Int, width: Int, height: Int
        ) {
            super.onSurfaceChanged(holder, format, width, height)
            engineHandler?.restart()
            LogUtil.d("HealingWallService HealingWallEngine onSurfaceChanged-----${this@HealingWallService}")
        }

        override fun onSurfaceDestroyed(holder: SurfaceHolder?) {
            super.onSurfaceDestroyed(holder)

            LogUtil.d("HealingWallService HealingWallEngine onSurfaceDestroyed-----${this@HealingWallService}")
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtil.d("HealingWallService onDestroy-----${this@HealingWallService}")
    }

    companion object {
        fun start(activity: Activity, wallInfo: WallInfo) {
            val intent: Intent = Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER).putExtra(
                WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
                ComponentName(activity, HealingWallService::class.java)
            )
            activity.startActivity(intent)
            App.spUtil.putString(Const.KEY_WALLS_NAME, wallInfo.handlerClassName)
            App.spUtil.putString(Const.KEY_WALLS_NAME, wallInfo.handlerClassName)
        }
    }


}