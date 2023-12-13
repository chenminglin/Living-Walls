package com.bethena.walls.fireworks

import android.content.Context
import android.graphics.*
import com.bethena.base_wall.BaseMaterialFactory
import com.bethena.base_wall.utils.ScreenUtil

class FireworksMaterialFactory : BaseMaterialFactory() {
    var paint = Paint()
    var fireworks = arrayListOf<Fireworks>()
    var flowers = arrayListOf<IFlower>()

    var backgroundPaint = Paint()
    lateinit var backgroundRect: RectF
    override fun provider(context: Context, canvas: Canvas, ratePer: Float) {
        paint.isAntiAlias = true
        paint.color = Color.WHITE
        paint.strokeWidth = ScreenUtil.dp2pxF(context, 1f)
        paint.style = Paint.Style.STROKE
        var cap = Paint.Cap.ROUND
        paint.strokeCap = cap


        backgroundPaint.shader = LinearGradient(
            0f, 0f,
            canvas.width.toFloat(), canvas.height.toFloat(),
            intArrayOf(Color.parseColor("#111C40"), Color.parseColor("#0E2A5A")),
            floatArrayOf(0f, 1f),
            Shader.TileMode.CLAMP
        )
        backgroundRect = RectF(0f, 0f, canvas.width.toFloat(), canvas.height.toFloat())
        initFireworks(context, canvas, ratePer)
//        initFlowers(context, canvas)

    }

    var maxFireworksCount = 7

    private fun initFireworks(context: Context, canvas: Canvas, ratePer: Float) {
        fireworks.clear()
        (0..maxFireworksCount).forEach {
            var firework = Fireworks()
            firework.provider(context, canvas, it, ratePer)
            fireworks.add(firework)
        }
    }

    private fun initFlowers(context: Context, canvas: Canvas) {
//        flowers.clear()
//        var f = Flower((canvas.width / 2).toFloat(), (canvas.height / 2).toFloat(), radius, radius2)

        var cx = canvas.width / 2f
        var cy = canvas.height / 2f
//        var f1 = Flower2()
//        f1.provider(context, canvas, cx, cy)
//        var f2 = Flower3()
//        f2.provider(context, canvas, cx, cy)
//        var f3 = Flower1()
//        f3.provider(context, canvas, cx, cy)
//        var f4 = Flower4()
//        f4.provider(context, canvas, cx, cy)
//        flowers.add(f1)
//        flowers.add(f2)
//        flowers.add(f3)
//        flowers.add(f4)
    }


    override fun doDraw(context: Context, canvas: Canvas) {
//        flowers.forEach {
//            it.draw(canvas)
//        }

        canvas.drawRect(backgroundRect, backgroundPaint)

        fireworks.forEach {
            it.draw(canvas)
        }

    }

    override fun destory() {
        fireworks.forEach {
            it.destory()
        }
    }
}