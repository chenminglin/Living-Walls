package com.bethena.healingwall.service

import android.app.Activity
import android.app.WallpaperManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.service.wallpaper.WallpaperService
import android.view.SurfaceHolder
import com.bethena.base_wall.BaseEngineHandler
import com.bethena.base_wall.WallInfo
import com.bethena.base_wall.utils.LogUtil
import com.bethena.healingwall.App
import com.bethena.healingwall.Const

class HealingWallService : WallpaperService() {

    override fun onCreate() {
        super.onCreate()
        LogUtil.d("HealingWallService onCreate-----")
//        App.appViewModel.wallChangeEvent.observe()
    }

    override fun onCreateEngine(): Engine {
        LogUtil.d("HealingWallService onCreateEngine-----")
        return HealingWallEngine()
    }


    inner class HealingWallEngine : Engine() {
        var engineHandler: BaseEngineHandler? = null
        var wallChangeListener = object : IHealingWallChange {
            override fun change() {
                engineHandler?.onDestroy()
                initHandler(getSurfaceHolder())
            }
        }

        override fun onCreate(surfaceHolder: SurfaceHolder?) {
            super.onCreate(surfaceHolder)
            initHandler(getSurfaceHolder())
            App.wallChanges.add(wallChangeListener)
            LogUtil.d(
                "HealingWallService HealingWallEngine onCreate-----",
                this@HealingWallService,
                this
            )
        }

        fun initHandler(surfaceHolder: SurfaceHolder?) {
            var handlerClassName = App.spUtil.getString(Const.KEY_WALLS_NAME)
            var handlerClass = Class.forName(handlerClassName)
                .asSubclass(BaseEngineHandler::class.java)
            var handler = handlerClass.getConstructor(Context::class.java)
                .newInstance(this@HealingWallService)
            engineHandler = handler as BaseEngineHandler?
            engineHandler?.surfaceCreated(surfaceHolder)
        }

        override fun onVisibilityChanged(visible: Boolean) {
            super.onVisibilityChanged(visible)
            LogUtil.d("HealingWallService HealingWallEngine onVisibilityChanged-----   ")
            engineHandler?.onVisibilityChanged(visible)
        }

        override fun onDestroy() {
            super.onDestroy()
            App.wallChanges.remove(wallChangeListener)
            LogUtil.d("HealingWallService HealingWallEngine onDestroy-----")
            engineHandler?.onDestroy()
        }


        override fun onSurfaceCreated(surfaceHolder: SurfaceHolder?) {
            super.onSurfaceCreated(surfaceHolder)
            LogUtil.d("HealingWallService HealingWallEngine onSurfaceCreated-----")
//            engineHandler?.onVisibilityChanged(visible)
        }

        override fun onSurfaceChanged(
            surfaceHolder: SurfaceHolder?, format: Int, width: Int, height: Int
        ) {
            super.onSurfaceChanged(surfaceHolder, format, width, height)
            engineHandler?.restart()
            LogUtil.d("HealingWallService HealingWallEngine onSurfaceChanged-----")
        }

        override fun onSurfaceDestroyed(surfaceHolder: SurfaceHolder?) {
            super.onSurfaceDestroyed(surfaceHolder)

            LogUtil.d("HealingWallService HealingWallEngine onSurfaceDestroyed-----")
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtil.d("HealingWallService onDestroy-----")
    }

    companion object {
        fun start(activity: Activity, wallInfo: WallInfo) {
            val intent: Intent = Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER).putExtra(
                WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
                ComponentName(activity, HealingWallService::class.java)
            )
            activity.startActivity(intent)
            App.spUtil.putString(Const.KEY_WALLS_NAME, wallInfo.handlerClassName)
            App.wallChanges.forEach {
                it.change()
            }
        }
    }


}