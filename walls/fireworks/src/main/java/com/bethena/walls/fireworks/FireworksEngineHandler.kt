package com.bethena.walls.fireworks

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import androidx.fragment.app.Fragment
import com.bethena.base_wall.BaseEngineHandler
import com.bethena.base_wall.utils.LogUtil

class FireworksEngineHandler(context: Context) : BaseEngineHandler(context) {

    var materialFactory: FireworksMaterialFactory? = null

    override fun initVariableMaterial() {
        mContext?.let {
            var canvas: Canvas? = lockCanvas() ?: return
            canvas?.let { c ->
                if (materialFactory == null) {
                    materialFactory = FireworksMaterialFactory()
                }
                materialFactory!!.provider(it, canvas, ratePer.value)
            }
            unlockCanvasAndPost(canvas)
        }
    }


    override fun doDraw() {
        LogUtil.d("doDraw refreshTime = $refreshTime")

        mainHandler.postDelayed({
            var canvas = lockCanvas() ?: return@postDelayed
            if (mContext == null) {
                return@postDelayed
            }
//            canvas.drawColor(Color.BLACK)

            materialFactory!!.doDraw(mContext!!, canvas)

//            canvas.drawColor(mashColor)
            unlockCanvasAndPost(canvas)
            doDraw()
        }, refreshTime)
    }

//    var matrix = Matrix()
//    var camera = Camera()
//    fun testDraw(canvas: Canvas) {
//        var r = ScreenUtil.dp2pxF(mContext!!, 80f)
//        canvas.save()
//        canvas.translate((canvas.width / 2).toFloat(), (canvas.height / 2).toFloat())
//        matrix.reset()
//        camera.save()
//        camera.rotateY(-87f)
//        camera.getMatrix(matrix)
//        canvas.concat(matrix)
//        var rectF = RectF(0f, -r, r, r)
//        canvas.drawRect(rectF, paint)
//        canvas.drawLine(0f, 0f, r * 2, 0f, paint)
//        camera.restore()
//        canvas.restore()
//    }


    override fun getMashValue(): Int {
        return 0
    }

    override fun newConfigFragment(): Fragment {
        return Fragment()
    }

    override fun onDestroy() {
        super.onDestroy()
        materialFactory?.destory()
    }
}