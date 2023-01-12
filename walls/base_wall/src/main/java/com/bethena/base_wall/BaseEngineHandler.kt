package com.bethena.base_wall

import android.content.Context
import android.graphics.Canvas
import android.graphics.PixelFormat
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.view.SurfaceHolder
import androidx.fragment.app.Fragment

abstract class BaseEngineHandler {
    var mSurfaceHolder: SurfaceHolder? = null
    var mContext: Context? = null
    lateinit var mainHandler: Handler

    var refreshRate = 60L

    protected var isVisible = false
    var spUtils: SpUtils? = null

    constructor(context: Context?) {
        mContext = context
        mainHandler = Handler(Looper.getMainLooper())
        mContext?.let {
            refreshRate = ScreenUtil.getScreenRefreshRate(it).toLong()
            spUtils = SpUtils(it, javaClass.name)
        }

    }

    abstract fun create()

    open fun create(surfaceHolder: SurfaceHolder?) {
        mSurfaceHolder = surfaceHolder
        mSurfaceHolder?.setFormat(PixelFormat.RGBA_8888)
    }

    fun lockCanvas(): Canvas? {
        var canvas = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            try {
                mSurfaceHolder?.lockHardwareCanvas()
            } catch (e: Exception) {
                mSurfaceHolder?.lockCanvas()
            }
        } else {
            mSurfaceHolder?.lockCanvas()
        }
        return canvas
    }

    open fun onVisibilityChanged(visible: Boolean) {
        isVisible = visible
    }

    open fun onDestroy() {
        pause()

    }

    fun restart() {
        pause()
        create()
        onVisibilityChanged(true)
    }

    abstract fun pause()

    abstract fun newConfigFragment(): Fragment

}