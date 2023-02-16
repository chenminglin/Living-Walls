package com.bethena.walls.fireworks

import android.content.Context
import android.graphics.*
import com.bethena.base_wall.utils.LogUtil
import com.bethena.base_wall.utils.RandomUtil
import com.bethena.base_wall.utils.ScreenUtil

class Fireworks {
    var x = 0f
    var endY = 0f
    var tailLenght = 0f
    var starY = 0f
    var initStarY = 0f
    var paint = Paint()
    var paint2 = Paint()

    var paintWidth = 0f
    lateinit var flower: IFlower

    var diffX = 0

    var increateY = 0f

    fun provider(context: Context, canvas: Canvas, index: Int) {
        paint.isAntiAlias = true
        paintWidth = ScreenUtil.dp2pxF(context, 3f)
        paint.strokeWidth = paintWidth
        paint.style = Paint.Style.STROKE
        paint.strokeCap = Paint.Cap.ROUND
        paint.color = Color.parseColor("#80F0EBAE")
        paint.setPathEffect(DiscretePathEffect(ScreenUtil.dp2pxF(context, 5f), 3.0F))

        paint2.strokeWidth = ScreenUtil.dp2pxF(context, 1f)
        paint2.color = Color.WHITE
        paint2.isAntiAlias = true
        paint2.style = Paint.Style.STROKE

        diffX = ScreenUtil.dp2px(context, 30f)
        x = randomX(canvas)
        endY = RandomUtil.nextFloat() * canvas.height / 2 + canvas.height / 10
        tailLenght = ScreenUtil.dp2pxF(context, 100f)
        starY =
            tailLenght + canvas.height + ScreenUtil.dp2px(context, 1000f) * RandomUtil.nextFloat()
        initStarY = starY

        increateY = ScreenUtil.dp2pxF(context,10f)

        providerFlower(context, canvas, x, endY, index)
    }

    fun providerFlower(context: Context, canvas: Canvas, x: Float, y: Float, index: Int) {
        var r = index % 4
        when (r) {
            0 -> {
                flower = Flower1()
            }
            1 -> {
                flower = Flower2()
            }
            2 -> {
                flower = Flower3()
            }
            3 -> {
                flower = Flower4()
            }
        }
        flower.provider(context, canvas, x, y)
    }

    fun draw(canvas: Canvas) {
        if (!isAnimFinish()) {
            var path = Path()
            if (animStatus == 0) {
//            canvas.drawLine(x, starY, x, starY - tailLenght, paint)
                var rect = RectF(x - paintWidth, starY - tailLenght, x + paintWidth, starY)
                path.addRect(rect, Path.Direction.CW)
            } else if (animStatus == 1) {
//            canvas.drawLine(x, starY, x, endY, paint)
                var rect = RectF(x - paintWidth, endY, x + paintWidth, starY)
                path.addRect(rect, Path.Direction.CW)
                LogUtil.d("x = $x starY = $starY endY = $endY  tailLenght = $tailLenght")
            }

            canvas.save()
            canvas.clipPath(path)
            canvas.drawLine(x, initStarY, x, endY, paint)

            canvas.restore()
//        canvas.drawPath(path, paint2)
            next()
        } else if (!flower.isAnimFinish()) {
            flower.draw(canvas)
        } else {
            reset(canvas)
        }
    }

    var animStatus = 0
    fun next() {
        if (animStatus == 0) {
            starY -= increateY
            if (starY - tailLenght <= endY) {
                animStatus = 1
            }
        } else if (animStatus == 1) {
            starY -= increateY
            if (starY <= endY) {
                animStatus = 2
            }
        }
    }

    fun reset(canvas: Canvas) {
        starY = initStarY
        animStatus = 0
        x = randomX(canvas)
        endY = RandomUtil.nextFloat() * canvas.height / 2 + canvas.height / 10
        flower.resetAnim(canvas, x, endY)
    }

    fun randomX(canvas: Canvas): Float {
        return RandomUtil.nextInt(canvas.width - diffX * 2).toFloat() + diffX
    }

    fun isAnimFinish(): Boolean {
        return animStatus == 2
    }

    fun destory(){
        flower.recycler()
    }

}