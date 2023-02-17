package com.bethena.walls.fireworks

import android.content.Context
import android.graphics.*
import com.bethena.base_wall.utils.LogUtil
import com.bethena.base_wall.utils.RandomUtil
import com.bethena.base_wall.utils.ScreenUtil
import kotlin.math.cos
import kotlin.math.sin

class Flower3 : IFlower {
    var x = 0f
    var y = 0f
    var maxRadius = 0f
    var startRadius = 0f
    var minRadius = 0f
    var eachDegree = 5
    var stepDegree = 12
    var maxAnimRadius = 0f
    var paint1 = Paint()
    var bitmapPaint1 = Paint()
    var paint2 = Paint()
    lateinit var maxBitmap: Bitmap
    lateinit var minBitmap: Bitmap
    var radiusIncreate = 5f

    var allScale = 1f
    
    override fun provider(context: Context, canvas: Canvas, x: Float, y: Float, ratePer: Float) {
        this.x = x
        this.y = y
        paint1.isAntiAlias = true
        paint1.style = Paint.Style.FILL
        paint1.strokeWidth = ScreenUtil.dp2pxF(context, 1f)
        paint1.color = Color.parseColor("#E34E82")

        paint2.isAntiAlias = true
        paint2.style = Paint.Style.FILL
        paint2.strokeWidth = ScreenUtil.dp2pxF(context, 1f)
        paint2.color = Color.parseColor("#D5EC44")

        maxRadius = ScreenUtil.dp2pxF(context, 50f)
        startRadius = ScreenUtil.dp2pxF(context, 6f)
        minRadius = ScreenUtil.dp2pxF(context, 40f)

        var maxBitmapLength = (maxRadius + startRadius) * 2
        maxBitmap = Bitmap.createBitmap(
            maxBitmapLength.toInt(),
            maxBitmapLength.toInt(),
            Bitmap.Config.ARGB_8888
        )
        var maxBitmapCanvas = Canvas(maxBitmap)
        var arcRectF1 = RectF(-maxRadius, -maxRadius, maxRadius, maxRadius)
        maxBitmapCanvas.translate(maxBitmapLength / 2f, maxBitmapLength / 2f)
        (0..360 step stepDegree).forEach {
            maxBitmapCanvas.save()
            var tx = cos(Math.toRadians(it.toDouble())) * startRadius
            var ty = sin(Math.toRadians(it.toDouble())) * startRadius
            maxBitmapCanvas.translate(tx.toFloat(), ty.toFloat())
            maxBitmapCanvas.drawArc(arcRectF1, it.toFloat(), eachDegree.toFloat(), true, paint1)
            maxBitmapCanvas.restore()
        }

        var minBitmapLength = (minRadius + startRadius) * 2
        minBitmap = Bitmap.createBitmap(
            minBitmapLength.toInt(),
            minBitmapLength.toInt(),
            Bitmap.Config.ARGB_8888
        )
        var minBitmapCanvas = Canvas(minBitmap)
        var arcRectF2 = RectF(-minRadius, -minRadius, minRadius, minRadius)
        minBitmapCanvas.translate(minBitmapLength / 2f, minBitmapLength / 2f)
        (eachDegree + 1..360 + eachDegree + 1 step stepDegree).forEach {
            minBitmapCanvas.save()
            var tx = cos(Math.toRadians(it.toDouble())) * startRadius
            var ty = sin(Math.toRadians(it.toDouble())) * startRadius
            minBitmapCanvas.translate(tx.toFloat(), ty.toFloat())
            minBitmapCanvas.drawArc(arcRectF2, it.toFloat(), eachDegree.toFloat(), true, paint2)
            minBitmapCanvas.restore()
        }

        radiusIncreate = ScreenUtil.dp2pxF(context,1f) * ratePer

        allScale = randomAllScale()
    }


    override fun draw(canvas: Canvas) {
        canvas.save()
        canvas.translate(x, y)
        canvas.scale(allScale, allScale)
        var pathClip = Path()
        pathClip.addCircle(0f, 0f, maxAnimRadius, Path.Direction.CW)
        canvas.save()
        if (animStatus == 0) {
            clipPath(canvas, pathClip)
        } else if (animStatus == 2) {
            clipOutPath(canvas, pathClip)
        }
//        canvas.drawPath(pathClip, paint2)

        if (animStatus < 3) {
            canvas.drawBitmap(
                maxBitmap,
                -maxBitmap.width / 2f,
                -maxBitmap.height / 2f,
                bitmapPaint1
            )
        }
        canvas.restore()

        if (animStatus >= 1) {
            canvas.save()
            if (animStatus == 1) {
                clipPath(canvas, pathClip)
            } else if (animStatus == 3) {
                clipOutPath(canvas, pathClip)
            }
            canvas.drawBitmap(
                minBitmap,
                -minBitmap.width / 2f,
                -minBitmap.height / 2f,
                bitmapPaint1
            )
            canvas.restore()
        }
        canvas.restore()

        next(canvas)
    }

    var animStatus: Int = 0
    override fun next(canvas: Canvas) {
        if (animStatus <= 3) {
            maxAnimRadius += radiusIncreate
            if (maxAnimRadius > maxBitmap.width / 2) {
                maxAnimRadius = 0f
                animStatus++
            }
        }
//        if (isAnimFinish()){
//            resetAnim(canvas)
//        }
        LogUtil.d("Flower3", "maxAnimRadius = $maxAnimRadius")
    }

    override fun recycler() {
        maxBitmap.recycle()
        minBitmap.recycle()
    }

    override fun isAnimFinish(): Boolean {
        return animStatus > 3
    }

    override fun resetAnim(canvas: Canvas, x: Float, y: Float) {
        this.x = x
        this.y = y
        animStatus = 0
        maxAnimRadius = 0f

        allScale = randomAllScale()
    }
}