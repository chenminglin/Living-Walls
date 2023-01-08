package com.bethena.living_walls.service

import android.service.wallpaper.WallpaperService
import android.view.SurfaceHolder
import com.bethena.living_walls.log.LogUtils
import com.bethena.walls.circle.CircleLivingWallEngineHandler

class LivingWallsService : WallpaperService() {


    override fun onCreate() {
        super.onCreate()
        LogUtils.d("LivingWallsService onCreate-----")
    }

    override fun onCreateEngine(): Engine {
        return LivingWallsEngine()
    }


    inner class LivingWallsEngine : Engine() {

        var engineHandler: CircleLivingWallEngineHandler? = null

        override fun onCreate(surfaceHolder: SurfaceHolder?) {
            super.onCreate(surfaceHolder)
            engineHandler = CircleLivingWallEngineHandler(this@LivingWallsService)
            engineHandler?.onCreate(surfaceHolder)
        }

        override fun onVisibilityChanged(visible: Boolean) {
            super.onVisibilityChanged(visible)

            engineHandler?.onVisibilityChanged(visible)
        }


        private fun doDraw() {

        }

        override fun onSurfaceCreated(holder: SurfaceHolder?) {
            super.onSurfaceCreated(holder)
            LogUtils.d("LivingWallsEngine onSurfaceCreated-----")
//            engineHandler?.onVisibilityChanged(visible)
        }

        override fun onSurfaceChanged(
            holder: SurfaceHolder?, format: Int, width: Int, height: Int
        ) {
            super.onSurfaceChanged(holder, format, width, height)

            LogUtils.d("LivingWallsEngine onSurfaceChanged-----")
        }

        override fun onSurfaceDestroyed(holder: SurfaceHolder?) {
            super.onSurfaceDestroyed(holder)

            LogUtils.d("LivingWallsEngine onSurfaceDestroyed-----")
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtils.d("LivingWallsService onDestroy-----")
    }


}