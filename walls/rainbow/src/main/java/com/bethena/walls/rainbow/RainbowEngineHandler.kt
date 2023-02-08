package com.bethena.walls.rainbow

import android.content.Context
import android.graphics.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bethena.base_wall.BaseEngineHandler
import com.bethena.base_wall.utils.*

class RainbowEngineHandler(context: Context) : BaseEngineHandler(context) {
    var backgroundColor = Color.parseColor("#73E1D5")
    var backgroundRect: RectF? = null
    var drawList = ArrayList<Any>()
    var backgroundPaint = Paint()
    var rainbowPaint = Paint()
    var shaderPaint = Paint()
    var cloudPaint = Paint()
    var starPaint = Paint()
    var star4Paint = Paint()
    var bitmapStar41: Bitmap? = null
    var bitmapStar42: Bitmap? = null
    var bitmapStar43: Bitmap? = null
    var cloudBitmap: Bitmap? = null
    var cloudBitmap2: Bitmap? = null
    var cloudBitmap3: Bitmap? = null
    var stars = ArrayList<StarCircle>()
    var star4s = ArrayList<Star4>()

    val LAST_RAINBOW_INDEX = 6

    override fun initVariableMaterial() {
        drawList.clear()
        mContext?.let {
            var canvas = lockCanvas()
            canvas?.let { c ->
                initRainbow(it, c)
                initStar(it, c)
                initStar4(it, c)
            }
            unlockCanvasAndPost(canvas)
        }
    }

    private fun initRainbow(context: Context, canvas: Canvas) {
        backgroundPaint.isAntiAlias = true
        var bColor1 = ContextCompat.getColor(context, R.color.rainbow_background_color1)
        var bColor2 = ContextCompat.getColor(context, R.color.rainbow_background_color2)
        backgroundPaint.shader = LinearGradient(
            0f, 0f, canvas.width.toFloat(), canvas.height.toFloat(),
            intArrayOf(bColor1, bColor2), floatArrayOf(0f, 1f),
            Shader.TileMode.CLAMP
        )
        backgroundRect = RectF(0f, 0f, canvas.width.toFloat(), canvas.height.toFloat())

        rainbowPaint.isAntiAlias = true
        rainbowPaint.style = Paint.Style.STROKE
        shaderPaint.isAntiAlias = true
        shaderPaint.style = Paint.Style.STROKE
        shaderPaint.strokeWidth = ScreenUtil.dp2pxF(context, 2f)
//        shaderPaint.strokeWidth = 0f
        shaderPaint.color = Color.BLACK
        shaderPaint.maskFilter =
            BlurMaskFilter(ScreenUtil.dp2pxF(context, 10f), BlurMaskFilter.Blur.OUTER)
        var strokWidth = ScreenUtil.dp2pxF(context, 60f)
//        var strokWidth = ScreenUtil.dp2pxF(context, 10f)
//        rainbowPaint.maskFilter = maskFilter
        rainbowPaint.strokeWidth = strokWidth
//        rainbowPaint.strokeCap = Paint.Cap.ROUND
        cloudPaint.isAntiAlias = true
        cloudPaint.color = Color.WHITE
        cloudPaint.alpha = 220

        starPaint.isAntiAlias = true
        starPaint.style = Paint.Style.FILL
        cloudBitmap = DrawableUtil.getDrawableToBitmap(context, R.drawable.cloud1, 0, 0.7f)
        cloudBitmap2 = DrawableUtil.getDrawableToBitmap(context, R.drawable.cloud2, 0, 1.5f)
        cloudBitmap3 = DrawableUtil.getDrawableToBitmap(context, R.drawable.cloud3, 0, 1.5f)

        var cx = if (canvas.width > canvas.height) {
            0
        } else {
            -canvas.width / 3
        }
//        var cx = canvas.width / 3
        var cy = canvas.height
//        var cy = canvas.height / 2
        val baseColorResName = "rainbow_color"
        var baseRadius = if (canvas.width > canvas.height) {
            canvas.width / 3
        } else {
            canvas.width / 3 * 2
        }

//        (0 until 1).forEach { i ->
        (0..LAST_RAINBOW_INDEX).forEach { i ->
            var color = AppUtil.getColorValueByResName(context, "$baseColorResName${i + 1}")
            var color11 = AppUtil.getColorValueByResName(context, "$baseColorResName${i + 1}1")
            var bitmapStar =
                DrawableUtil.getDrawableToBitmap(context, R.drawable.ig_rainbow_star, color, 2.5f)
            var rainbow = Rainbow(
                color, color11,
                cx.toFloat(),
                cy.toFloat(),
                baseRadius + (strokWidth * i),
                bitmapStar!!
            )
            rainbow.paintHalfWidth = rainbowPaint.strokeWidth / 2 - shaderPaint.strokeWidth
            rainbow.degreeIncreate = rainbow.degreeIncreate * ratePer.value
            rainbow.alphaIncreate = rainbow.alphaIncreate * ratePer.value
            rainbow.starDegreeIncreate = rainbow.starDegreeIncreate * ratePer.value
            if (i == 0) {
                var cloudY = canvas.height * 2 / 3
                cloudBitmap?.let { b ->
                    var cloud1 = Cloud(
                        b, (-b.width).toFloat(), cloudY.toFloat()
                    )
                    cloud1.leftIncreate = cloud1.leftIncreate * ratePer.value
                    drawList.add(cloud1)
                }
            }
            drawList.add(rainbow)
            if (i == 5) {
                var cloudY = canvas.height / 3
                cloudBitmap2?.let { b ->
                    var cloud1 = Cloud(
                        b, (canvas.width / 2).toFloat(), cloudY.toFloat()
                    )
                    cloud1.leftIncreate = cloud1.leftIncreate * ratePer.value
                    drawList.add(cloud1)
                }
            } else if (i == 1) {
                var cloudY = canvas.height / 2
                cloudBitmap3?.let { b ->
                    var cloud1 = Cloud(
                        b, (canvas.width / 3).toFloat(), cloudY.toFloat()
                    )
                    cloud1.leftIncreate = cloud1.leftIncreate * ratePer.value
                    drawList.add(cloud1)
                }
            }
        }
        drawList.reverse()
    }

    private fun initStar(context: Context, canvas: Canvas) {
        stars.clear()
        var widht = canvas.width
        var height = canvas.height
//        var starCount = random.nextInt(10) + 5
        var MAX_RADIUS = ScreenUtil.dp2px(context, 3f)
        var WIDTH_COUNT = 4
        var HEIGHT_COUNT = 4
        var each_width = widht / WIDTH_COUNT
        var each_height = height / HEIGHT_COUNT
//        var colors = intArrayOf(spaceColor.star_color1, spaceColor.star_color2)
        (0 until WIDTH_COUNT).forEach { x_d ->
            (0 until HEIGHT_COUNT).forEach { y_d ->
                var x = RandomUtil.nextInt(each_width) + x_d * each_width + MAX_RADIUS
                var y = RandomUtil.nextInt(each_height) + y_d * each_height + MAX_RADIUS
                var radius = (RandomUtil.nextFloat() + 0.3f) * MAX_RADIUS
                var alpha = RandomUtil.nextInt(150) + 105
                var star = StarCircle(x.toFloat(), y.toFloat(), radius, alpha, Color.WHITE)
                star.alphaIncreate = ((RandomUtil.nextInt(3) + 1).toFloat()) * ratePer.value
                stars.add(star)
            }
        }
    }

    private fun initStar4(context: Context, canvas: Canvas) {
        star4Paint.isAntiAlias = true
        bitmapStar41 = DrawableUtil.getDrawableToBitmap(context, R.drawable.star4, 0, 1.2f)
        bitmapStar42 = DrawableUtil.getDrawableToBitmap(context, R.drawable.star4, 0, 1.0f)
        bitmapStar43 = DrawableUtil.getDrawableToBitmap(context, R.drawable.star4, 0, 1.5f)
        var bitmaps = arrayOf(bitmapStar41, bitmapStar42, bitmapStar43)
        star4s.clear()
        var widht = canvas.width
        var height = canvas.height
//        var starCount = random.nextInt(10) + 5
        var MAX_RADIUS = ScreenUtil.dp2px(context, 3f)
        var WIDTH_COUNT = 3
        var HEIGHT_COUNT = 3
        var each_width = widht / WIDTH_COUNT
        var each_height = height / HEIGHT_COUNT
//        var colors = intArrayOf(spaceColor.star_color1, spaceColor.star_color2)
        (0 until WIDTH_COUNT).forEach { x_d ->
            (0 until HEIGHT_COUNT).forEach { y_d ->
                var x = RandomUtil.nextInt(each_width) + x_d * each_width + MAX_RADIUS
                var y = RandomUtil.nextInt(each_height) + y_d * each_height + MAX_RADIUS
                var alpha = RandomUtil.nextInt(150) + 105
                var bitmap = bitmaps[RandomUtil.nextInt(3)]
                var star = Star4(x.toFloat(), y.toFloat(), bitmap!!, alpha)
//                star.alphaIncreate = ((RandomUtil.nextInt(3) + 1).toFloat())
                star.scaleIncreate = star.scaleIncreate * ratePer.value
                star4s.add(star)
            }
        }
    }

    override fun doDraw() {
        mainHandler.postDelayed({
            var canvas = lockCanvas()
            canvas?.let {
//                it.drawColor(backgroundColor)
                backgroundRect?.let { it1 -> it.drawRect(it1, backgroundPaint) }
                var isAllFallFinish = true
                var rainbowIndex = 0
                drawList.forEach { a ->
                    if (a is Rainbow) {
                        rainbowPaint.color = a.color

                        var startAngle = -90f
                        canvas.save()

                        canvas.rotate(startAngle, a.cx, a.cy)
                        rainbowPaint.alpha = a.alpha.toInt()
                        shaderPaint.alpha = a.alpha.toInt()
//                        rainbowPaint.maskFilter = null
//                        rainbowPaint.shader = a.shader.value
//                        rainbowPaint.style = Paint.Style.FILL
//                        canvas.drawArc(arcRect, startAngle, a.degree, false, rainbowPaint)
//                        var maskFilter = BlurMaskFilter(30f, BlurMaskFilter.Blur.SOLID)
//                        rainbowPaint.maskFilter = maskFilter
//                        rainbowPaint.setShadowLayer(a.radius, a.cx, a.cy, a.color)
//                        shaderPaint.color = a.color
                        if (rainbowIndex != 0) {
                            canvas.drawArc(a.arcShadowRect.value, 0f, a.degree, false, shaderPaint)
                        }
                        canvas.drawArc(a.arcRect, 0f, a.degree, false, rainbowPaint)
//                        canvas.drawCircle(a.cx, a.cy, a.radius, rainbowPaint)
                        canvas.restore()
                        var starDegree = a.degree + 5
                        var starX =
                            a.cx + ((a.radius) * Math.sin(Math.toRadians(starDegree.toDouble()))) - a.starBitmap.width / 2
                        var starY =
                            a.cy - (a.radius) * Math.cos(Math.toRadians(starDegree.toDouble())) - a.starBitmap.height / 2

                        LogUtil.d("cx = ${a.cx} cy = ${a.cy}")
                        LogUtil.d("realDegree = $starDegree a.radius = ${a.radius} , starX = $starX starY=$starY")
                        a.starBitmap.let { bit ->
//                            var deltaX =
//                                a.starDiagonal.value * Math.cos(Math.toRadians(realDegree.toDouble()))
//                            var deltaY =
//                                a.starDiagonal.value * Math.sin(Math.toRadians(realDegree.toDouble()))
//                            LogUtil.d("realDegree = $realDegree    deltaX.toFloat() = ${deltaX.toFloat()}")
//                            var positionMatrix = Matrix()
//                            positionMatrix.setTranslate(
////                                starX.toFloat() - deltaX.toFloat(),
//                                starX.toFloat(),
//                                starY.toFloat()
//                            )
//
//                            var rotateMatrix = Matrix()
//                            rotateMatrix.setRotate(
//                                a.starDegree,
//                                bit.width.toFloat() / 2,
//                                bit.height.toFloat() / 2
//                            )
//                            positionMatrix.preConcat(rotateMatrix)
//                            rainbowPaint.maskFilter = null
                            canvas.drawBitmap(
                                bit,
                                a.getStarMatrix(
                                    starX.toFloat(),
                                    starY.toFloat(),
                                    bit.width.toFloat(),
                                    bit.height.toFloat()
                                ),
                                rainbowPaint
                            )
                        }
                        a.next()

                        if (!a.isFallFinish()) {
                            isAllFallFinish = false
                        }

                        rainbowIndex++
                    } else if (a is Cloud) {
                        if (!a.bitmap.isRecycled) {
                            canvas.drawBitmap(a.bitmap, a.left, a.top, cloudPaint)
                            a.next(canvas)
                        }
                    }
                }
                if (isAllFallFinish) {
//                    return@postDelayed
                    var isAllAlphaFinish = true
                    drawList.forEach {
                        if (it is Rainbow) {
                            if (it.isFallState()) {
                                it.toAlpha()
                            }

                            if (!it.isAllFinish()) {
                                isAllAlphaFinish = false
                            }
                        }
                    }
                    if (isAllAlphaFinish) {
                        drawList.forEach {
                            if (it is Rainbow) {
                                it.reset()
                            }
                        }
                    }
                }

                stars.forEach { star ->
                    starPaint.color = star.color
                    starPaint.strokeWidth = star.radius
                    starPaint.alpha = star.alpha
                    canvas.drawCircle(star.x, star.y, star.radius, starPaint)
                    star.next()
                }

                star4s.forEach { star4 ->
                    if (star4.bitmap.isRecycled) {
                        return@forEach
                    }
                    canvas.save()
                    canvas.scale(star4.scale, star4.scale, star4.cx, star4.cy)
//                    canvas.rotate(star4.degree.toFloat(), star4.cx, star4.cy)
                    canvas.drawBitmap(
                        star4.bitmap,
                        star4.cx - star4.bitmap.width / 2,
                        star4.cy - star4.bitmap.height / 2,
                        star4Paint
                    )
                    canvas.restore()
                    star4.next()
                }
                canvas.drawColor(mashColor)
            }
            unlockCanvasAndPost(canvas)
            doDraw()
        }, refreshTime)
    }

    override fun getMashValue(): Int {
        return spUtils.getInt(RainbowConst.KEY_MASH_PERCENT, 0)
    }

    override fun onDestroy() {
        super.onDestroy()
        recycleBitmap(bitmapStar41)
        recycleBitmap(bitmapStar42)
        recycleBitmap(bitmapStar43)
        recycleBitmap(cloudBitmap)
        recycleBitmap(cloudBitmap2)
        recycleBitmap(cloudBitmap3)
    }

    private fun recycleBitmap(bitmap: Bitmap?) {
        bitmap?.let {
            if (!it.isRecycled) {
                it.recycle()
            }
        }
    }

    override fun newConfigFragment(): Fragment {
        return RainbowConfigFragment()
    }
}