package com.bethena.walls.thunder_breath

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import androidx.fragment.app.Fragment
import com.bethena.base_wall.BaseEngineHandler
import com.bethena.base_wall.utils.LogUtil
import com.bethena.base_wall.utils.ScreenUtil

class ThunderBreathEngineHandler(context: Context?) : BaseEngineHandler(context) {

    var paint = Paint()

    var thunderPath: ThunderPath? = null

    override fun initVariableMaterial() {
        mContext?.let {
            paint.isAntiAlias = true
            paint.color = Color.WHITE
            paint.strokeWidth = ScreenUtil.dp2pxF(it, 6f)
            paint.style = Paint.Style.STROKE
//            paint.strokeCap
//            paint.setPathEffect(MyPathEffect())
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
            canvas.save()
            canvas.drawColor(Color.BLACK)
            thunderPath!!.path.fillType
            canvas.drawPath(thunderPath!!.path, paint)
//            canvas.drawLine(0f, 0f, canvas.width.toFloat(), canvas.height.toFloat(), paint)

            unlockCanvasAndPost(canvas)
//            doDraw()
        }, refreshTime)
    }

    override fun newConfigFragment(): Fragment {
        return Fragment()
    }
}