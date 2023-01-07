package com.bethena.living_walls.service

import android.graphics.Color
import android.graphics.Paint
import android.graphics.PixelFormat
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.service.wallpaper.WallpaperService
import android.view.SurfaceHolder
import com.shyz.media_code.log.LogUtils

class LivingWallsService : WallpaperService() {

    override fun onCreate() {
        super.onCreate()
        LogUtils.d("LivingWallsService onCreate-----")
    }

    override fun onCreateEngine(): Engine {
        return LivingWallsEngine()
    }


    inner class LivingWallsEngine : Engine() {

        var mHolder: SurfaceHolder? = null
        var mMainHandler: Handler? = null
        private var degrees: Float = 0f
        private var paint = Paint()
        override fun onCreate(surfaceHolder: SurfaceHolder?) {
            super.onCreate(surfaceHolder)
            mHolder = surfaceHolder
            mHolder?.setFormat(PixelFormat.RGBA_8888)
            paint.isAntiAlias = true
            paint.color = Color.WHITE
            paint.strokeWidth = 20f
            LogUtils.d("LivingWallsEngine onCreate-----")
        }

        override fun onVisibilityChanged(visible: Boolean) {
            super.onVisibilityChanged(visible)
            LogUtils.d("LivingWallsEngine onVisibilityChanged-----")
            LogUtils.d("visible = $visible")
            mMainHandler = Handler(Looper.getMainLooper())

            if (visible) {
                doDraw()
            }

        }


        private fun doDraw() {
            if (!isVisible) {
                LogUtils.d("doDraw return..")
                return
            }
//            if (degrees > 5) {
//                return
//            }

            if (degrees>=360){
                degrees = 0f
            }
            mMainHandler?.postDelayed(Runnable {
                mHolder?.let {
                    LogUtils.d("doDrawing..degree = $degrees")
                    var canvas = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                        it.lockHardwareCanvas()
                        it.lockCanvas()
                    } else {
                        it.lockCanvas()
                    }

                    var width = canvas.width
                    var height = canvas.height
//                    canvas.drawARGB(0, 0, 0, 0)
                    canvas.save()
//                    canvas.
                    canvas.drawColor(Color.BLACK)
//                    canvas.drawARGB(0, 0, 0, 0)

                    canvas.rotate(degrees, width / 2f, height / 2f)

                    canvas.drawLine(0f, 0f, width.toFloat(), height.toFloat(), paint)
                    canvas.restore()
                    it.unlockCanvasAndPost(canvas)
                    degrees = degrees + 1f
                    doDraw()
                }
            }, 10)
        }

        override fun onSurfaceCreated(holder: SurfaceHolder?) {
            super.onSurfaceCreated(holder)
            LogUtils.d("LivingWallsEngine onSurfaceCreated-----")
        }

        override fun onSurfaceChanged(
            holder: SurfaceHolder?,
            format: Int,
            width: Int,
            height: Int
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