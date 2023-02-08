package com.bethena.walls.thunder_breath

import android.content.Context
import android.graphics.*
import androidx.fragment.app.Fragment
import com.bethena.base_wall.BaseEngineHandler
import com.bethena.base_wall.utils.LogUtil
import com.bethena.base_wall.utils.ScreenUtil

class ThunderBreathEngineHandler(context: Context?) : BaseEngineHandler(context) {

    var paint = Paint()

    var thunderPath: ThunderPath? = null

    var maskRadius = 100f

    override fun initVariableMaterial() {
        mContext?.let {
            paint.isAntiAlias = true
            paint.color = Color.WHITE
            paint.strokeWidth = ScreenUtil.dp2pxF(it, 6f)
            paint.style = Paint.Style.STROKE
//            var maskFilter = BlurMaskFilter(100f, BlurMaskFilter.Blur.SOLID)
//            paint.maskFilter = maskFilter
            var pathEffect1 = DiscretePathEffect(50f, 30f)
//            paint.setPathEffect(pathEffect1)
//            paint.strokeMiter = 5f


        }
    }


    override fun doDraw() {
        LogUtil.d("doDraw")

        mainHandler.postDelayed({
            var canvas = lockCanvas() ?: return@postDelayed
            if (thunderPath == null) {
                thunderPath = ThunderPath()
                thunderPath!!.randomPath(canvas.width.toFloat(), canvas.height.toFloat())
            }
            var maskFilter = BlurMaskFilter(maskRadius, BlurMaskFilter.Blur.SOLID)
            paint.maskFilter = maskFilter
            paint.strokeWidth = ScreenUtil.dp2pxF(mContext!!, 6f)

//            paint.setShadowLayer(maskRadius, 0f, 0f, Color.YELLOW)
            canvas.save()
            canvas.drawColor(Color.BLACK)
            paint.shader = LinearGradient(0f, 0f, canvas.width.toFloat(), canvas.height.toFloat(),
                intArrayOf(Color.RED, Color.GREEN, Color.BLUE),
                floatArrayOf(0f, 0.5f, 1.0f),
                Shader.TileMode.CLAMP)
            var index = 1
            thunderPath!!.segments.forEach {
                paint.strokeWidth = ScreenUtil.dp2pxF(mContext!!, 6f * index)
                canvas.drawPath(it, paint)
                index++
            }

//            canvas.drawPath(thunderPath!!.path, paint)
            paint.shader = null
            paint.color = Color.WHITE
            paint.strokeWidth = ScreenUtil.dp2pxF(mContext!!, 1f)
            canvas.drawPath(thunderPath!!.path, paint)
//            canvas.drawLine(0f, 0f, canvas.width.toFloat(), canvas.height.toFloat(), paint)

            unlockCanvasAndPost(canvas)
            doDraw()
        }, refreshTime)
    }

    override fun getMashValue(): Int {
        TODO("Not yet implemented")
    }

    override fun newConfigFragment(): Fragment {
        return Fragment()
    }
}