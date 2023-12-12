package com.bethena.walls.fireworks

import android.content.Context
import android.graphics.*
import com.bethena.base_wall.utils.ColorUtil
import com.bethena.base_wall.utils.LogUtil
import com.bethena.base_wall.utils.RandomUtil
import com.bethena.base_wall.utils.ScreenUtil
import kotlin.math.cos

class Flower1 : IFlower {

    var x = 0f
    var y = 0f
    var radius1 = 0f
    var radius2 = 0f
    var radius3 = 0f
    var radius4 = 0f
    var startRadius = 0f

    var paint1 = Paint()
    var paintBitmap = Paint()
    var paint2 = Paint()

    var matrix = Matrix()
    var camera = Camera()
    var allScale = 1f

    var rotateYDegree = -87f
    var bitmapWidth = 0
    lateinit var bitmap1: Bitmap
    lateinit var bitmap2: Bitmap
    lateinit var bitmap3: Bitmap
    lateinit var bitmap4: Bitmap

    var animRadius1 = 0f
    var animRadius2 = 0f
    var animRadius3 = 0f
    var animRadius4 = 0f
    var alpha = 255f

    var animRadiusIncreate1 = 0f
    var animRadiusIncreate2 = 0f
    var animRadiusIncreate3 = 0f
    var animRadiusIncreate4 = 0f
    var alphaIncreate = 1f


    override fun provider(context: Context, canvas: Canvas, x: Float, y: Float, ratePer: Float) {
        paint1.isAntiAlias = true

        paint1.strokeWidth = ScreenUtil.dp2pxF(context, 0.4f)
        paint1.style = Paint.Style.STROKE
        paint1.strokeCap = Paint.Cap.ROUND
//        paint1.strokeMiter = 2f
        this.x = x
        this.y = y
        radius1 = ScreenUtil.dp2pxF(context, 174f)
        radius2 = ScreenUtil.dp2pxF(context, 164f)
        radius3 = ScreenUtil.dp2pxF(context, 170f)
        radius4 = ScreenUtil.dp2pxF(context, 168f)
        startRadius = ScreenUtil.dp2pxF(context, 90f)

        paint1.color = ColorUtil.randomColor()
        bitmap1 = providerBitmap(radius1, 0)
        paint1.color = ColorUtil.randomColor()
        bitmap2 = providerBitmap(radius2, 8)
        paint1.color = ColorUtil.randomColor()
        bitmap3 = providerBitmap(radius3, 16)
        paint1.color = ColorUtil.randomColor()
        bitmap4 = providerBitmap(radius4, 24)

        animRadiusIncreate1 = ScreenUtil.dp2pxF(context, 0.5f) * ratePer
        animRadiusIncreate2 = ScreenUtil.dp2pxF(context, 0.2f) * ratePer
        animRadiusIncreate3 = ScreenUtil.dp2pxF(context, 0.3f) * ratePer
        animRadiusIncreate4 = ScreenUtil.dp2pxF(context, 0.4f) * ratePer
        alphaIncreate = 2 * ratePer

        allScale = randomAllScale()

    }

    private fun providerBitmap(radius: Float, starDegree: Int): Bitmap {
        var bitmapWidth =
            ((cos(Math.toRadians(rotateYDegree.toDouble())) * radius + startRadius).toInt()) * 2
        var bitmap = Bitmap.createBitmap(bitmapWidth, bitmapWidth, Bitmap.Config.ARGB_8888)
        var bitmap1Canvas = Canvas(bitmap)
        bitmap1Canvas.translate((bitmapWidth / 2).toFloat(), (bitmapWidth / 2).toFloat())
        (starDegree..360 + starDegree step 36).forEach {
            bitmap1Canvas.save()
            matrix.reset()
            camera.save()
            camera.rotateZ(it.toFloat())

            camera.rotateY(rotateYDegree)
            camera.getMatrix(matrix)
            bitmap1Canvas.concat(matrix)
            bitmap1Canvas.drawLine(startRadius, 0f, radius, 0f, paint1)
            camera.restore()
            bitmap1Canvas.restore()
        }
        return bitmap
    }

    override fun draw(canvas: Canvas) {
        canvas.save()
        canvas.translate(x, y)

        canvas.scale(allScale, allScale)
//        paint1.color = Color.WHITE

        paintBitmap.alpha = alpha.toInt()

        drawBitmap(animRadius1, canvas, bitmap1, paintBitmap)
        drawBitmap(animRadius2, canvas, bitmap2, paintBitmap)
        drawBitmap(animRadius3, canvas, bitmap3, paintBitmap)
        drawBitmap(animRadius4, canvas, bitmap4, paintBitmap)
//        canvas.drawBitmap(bitmap2, -bitmap2.width / 2f, -bitmap2.height / 2f, paintBitmap)
//        canvas.drawBitmap(bitmap3, -bitmap3.width / 2f, -bitmap3.height / 2f, paintBitmap)
//        canvas.drawBitmap(bitmap4, -bitmap4.width / 2f, -bitmap4.height / 2f, paintBitmap)
//        canvas.drawBitmap(bitmap5, -bitmap5.width / 2f, -bitmap5.height / 2f, paintBitmap)

//        paint1.color = Color.RED
//        canvas.drawLine(0f, 0f, bitmapWidth.toFloat(), 0f, paint1)

//        var path = Path()
        canvas.restore()
        next(canvas)
    }

    private fun drawBitmap(animRadius: Float, canvas: Canvas, bitmap: Bitmap, paint: Paint) {
        canvas.save()
        var path = Path()
        path.addCircle(0f, 0f, animRadius, Path.Direction.CW)
        clipPath(canvas, path)
        canvas.drawBitmap(bitmap, -bitmap.width / 2f, -bitmap.height / 2f, paint)
        canvas.restore()
    }

    override fun next(canvas: Canvas) {
        if (isFirstAnimFinish() && alpha > 0) {
            alpha -= alphaIncreate
        } else {
            animRadius1 += animRadiusIncreate1
            animRadius2 += animRadiusIncreate2
            animRadius3 += animRadiusIncreate3
            animRadius4 += animRadiusIncreate4
        }

//        if (isAnimFinish()){
//            resetAnim(canvas)
//        }

        LogUtil.d("alpha = $alpha")
    }

    private fun isFirstAnimFinish(): Boolean {
        return animRadius1 >= bitmap1.width / 2
                && animRadius2 >= bitmap2.width / 2
                && animRadius3 >= bitmap3.width / 2
                && animRadius4 >= bitmap4.width / 2
    }

    override fun recycler() {
        bitmap1.recycle()
        bitmap2.recycle()
        bitmap3.recycle()
        bitmap4.recycle()
    }

    override fun isAnimFinish(): Boolean {
        return alpha <= 0
    }

    override fun resetAnim(canvas: Canvas, x: Float, y: Float) {
        this.x = x
        this.y = y
        animRadius1 = 0f
        animRadius2 = 0f
        animRadius3 = 0f
        animRadius4 = 0f
        alpha = 255f

        allScale = randomAllScale()

    }


}