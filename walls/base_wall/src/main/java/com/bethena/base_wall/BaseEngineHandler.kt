package com.bethena.base_wall

import android.content.Context
import android.graphics.Canvas
import android.graphics.PixelFormat
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.view.SurfaceHolder
import androidx.fragment.app.Fragment
import com.bethena.base_wall.utils.LogUtil
import com.bethena.base_wall.utils.ScreenUtil
import com.bethena.base_wall.utils.SpUtil

abstract class BaseEngineHandler {
    var mSurfaceHolder: SurfaceHolder? = null
    var mContext: Context? = null
    lateinit var mainHandler: Handler

    var refreshRate = 60L

    protected var isVisible = false
    var spUtils: SpUtil? = null


    //在子类的init前面调用
    constructor(context: Context?) {
        LogUtil.d("BaseEngineHandler constructor")
        mContext = context
        mainHandler = Handler(Looper.getMainLooper())
        mContext?.let {
            refreshRate = ScreenUtil.getScreenRefreshRate(it).toLong()
            spUtils = SpUtil(it, javaClass.name)
        }

    }

    /**
     * 初始化可变素材
     */
    abstract fun initVariableMaterial()

    /**
     * surfaceHolder创建调用
     */
    open fun surfaceCreated(surfaceHolder: SurfaceHolder?) {
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
        LogUtil.d("BaseEngineHandler onVisibilityChanged visible = $visible mContext = $mContext")
        if (mContext == null) {
            return
        }

        if (visible) {
//            testDraw()
            initVariableMaterial()
            doDraw()
        } else {
            mainHandler.removeCallbacksAndMessages(null)
        }
    }

    open fun onDestroy() {
        pause()

    }

    fun restart() {
        pause()
        onVisibilityChanged(true)
    }



    abstract fun pause()
    protected abstract fun doDraw()

    abstract fun newConfigFragment(): Fragment

}