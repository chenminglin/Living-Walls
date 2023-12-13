package com.bethena.base_wall

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.PixelFormat
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.view.SurfaceHolder
import androidx.fragment.app.Fragment
import com.bethena.base_wall.utils.ColorUtil
import com.bethena.base_wall.utils.LogUtil
import com.bethena.base_wall.utils.ScreenUtil
import com.bethena.base_wall.utils.SpUtil

abstract class BaseEngineHandler {
    var mSurfaceHolder: SurfaceHolder? = null
    var mContext: Context? = null
    lateinit var mainHandler: Handler

    var refreshRate = 60L
    var refreshTime = 0L
    var baseRefreshTime = 1000 / 60

    var ratePer = lazy {
        refreshTime / baseRefreshTime.toFloat()
    }

    protected var isVisible = false
    lateinit var spUtils: SpUtil

    var mashColor = Color.parseColor("#00ffffff")

    //在子类的init前面调用
    constructor(context: Context?) {
        LogUtil.d("BaseEngineHandler constructor")
        mContext = context
        mainHandler = Handler(Looper.getMainLooper())
        mContext?.let {
            refreshRate = ScreenUtil.getScreenRefreshRate(it).toLong()
            spUtils = SpUtil(it, javaClass.name)
            refreshTime = 1000 / refreshRate
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

    fun unlockCanvasAndPost(canvas: Canvas?) {
        try {
            mSurfaceHolder?.unlockCanvasAndPost(canvas)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    open fun onVisibilityChanged(visible: Boolean) {
        isPaused = false
        isVisible = visible
        LogUtil.d(
            "BaseEngineHandler onVisibilityChanged visible = ",
            visible,
            "mContext = ",
            mContext,
            "refreshTime = ", refreshTime
        )
        if (mContext == null) {
            return
        }
        if (refreshTime == 0L) {
            refreshTime = 1000 / 60
        }

        if (visible) {
//            testDraw()
            resetMash()
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

    var isPaused = false


    open fun pause() {
        isPaused = true
        mainHandler.removeCallbacksAndMessages(null)
    }


    protected abstract fun doDraw()

    abstract fun getMashValue(): Int

    fun resetMash() {
        mashColor = ColorUtil.adjustAlpha(
            Color.BLACK, getMashValue() / 100f
        )
    }

    abstract fun newConfigFragment(): Fragment

}