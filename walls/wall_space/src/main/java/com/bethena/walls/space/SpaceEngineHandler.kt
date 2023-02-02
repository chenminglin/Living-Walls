package com.bethena.walls.space

import android.content.Context
import android.graphics.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bethena.base_wall.BaseEngineHandler
import com.bethena.base_wall.utils.DrawableUtil
import com.bethena.base_wall.utils.LogUtil
import com.bethena.base_wall.utils.RandomUtil
import com.bethena.base_wall.utils.ScreenUtil
import java.util.*

class SpaceEngineHandler(context: Context?) : BaseEngineHandler(context) {

    var starPaint = Paint()

    var backPaint = Paint()
    var ringPaint = Paint()
    var backColorStart = 0
    var backColorCenter = 0

    var planetPaint = Paint()
    var bigPlanetPaint = Paint()

    var meteorPaint = Paint()

    var degree = 0f

    override fun initVariableMaterial() {
        mContext?.let {
            starPaint.isAntiAlias = true
            starPaint.color = Color.WHITE
            starPaint.strokeWidth = ScreenUtil.dp2pxF(it, 6f)
            starPaint.style = Paint.Style.FILL

            planetPaint.isAntiAlias = true
            ringPaint.isAntiAlias = true
            ringPaint.style = Paint.Style.STROKE
            ringPaint.strokeWidth = ScreenUtil.dp2pxF(it, 2f)

            bigPlanetPaint.isAntiAlias = true
            bigPlanetPaint.color = Color.parseColor("#FF04A9ED")
            var maskFilter = BlurMaskFilter(100f, BlurMaskFilter.Blur.SOLID)
            bigPlanetPaint.maskFilter = maskFilter

            meteorPaint.isAntiAlias = true
            meteorPaint.style = Paint.Style.STROKE
            meteorPaint.strokeWidth = ScreenUtil.dp2pxF(it, 2f)
            meteorPaint.color = Color.WHITE
            meteorPaint.strokeCap = Paint.Cap.ROUND

            backColorStart = ContextCompat.getColor(it, R.color.background_color_start)
            backColorCenter = ContextCompat.getColor(it, R.color.background_color_center)
            var canvas: Canvas? = lockCanvas() ?: return
            initStars(canvas!!)
            initPlanet(canvas!!)
            initMeteor(canvas)
            initBigPlanet(canvas)
            unlockCanvasAndPost(canvas)
        }
    }

    var stars = ArrayList<Star>()
    var random = Random(System.currentTimeMillis())
    fun initStars(canvas: Canvas) {
        stars.clear()
        var widht = canvas.width
        var height = canvas.height
//        var starCount = random.nextInt(10) + 5
        var MAX_RADIUS = ScreenUtil.dp2px(mContext!!, 3f)
        var WIDTH_COUNT = 4
        var HEIGHT_COUNT = 4
        var each_width = (widht - 2 * MAX_RADIUS) / WIDTH_COUNT
        var each_height = (height * 0.4f - 2 * MAX_RADIUS) / HEIGHT_COUNT
        var colors = intArrayOf(Color.parseColor("#FFF7B500"), Color.WHITE)
        (0 until WIDTH_COUNT).forEach { x_d ->
            (0 until HEIGHT_COUNT).forEach { y_d ->
                var x = random.nextInt(each_width) + x_d * each_width + MAX_RADIUS
                var y = random.nextInt(each_height.toInt()) + y_d * each_height + MAX_RADIUS
                var radius = (random.nextFloat() + 0.3f) * MAX_RADIUS
                var alpha = random.nextInt(150) + 105
                var colorIndex = RandomUtil.nextInt(2)
                var star = Star(x.toFloat(), y, radius, alpha, colors[colorIndex])
                stars.add(star)
            }
        }

        var nextHeightPer = 0.7f//剩下的星星在屏幕的下方
        var nextStarY = height * nextHeightPer
        var WIDTH_COUNT2 = 4
        var HEIGHT_COUNT2 = 3
        var each_width2 = (widht - 2 * MAX_RADIUS) / WIDTH_COUNT2
        var each_height2 = (height * (1 - nextHeightPer) - 2 * MAX_RADIUS) / HEIGHT_COUNT2
        (0 until WIDTH_COUNT2).forEach { x_d ->
            (0 until HEIGHT_COUNT2).forEach { y_d ->
                var x = random.nextInt(each_width2) + x_d * each_width2 + MAX_RADIUS
                var y =
                    random.nextInt(each_height2.toInt()) + y_d * each_height2 + MAX_RADIUS + nextStarY
                var radius = (random.nextFloat() + 0.3f) * MAX_RADIUS
                var alpha = random.nextInt(150) + 105
                var colorIndex = RandomUtil.nextInt(2)
                var star = Star(x.toFloat(), y, radius, alpha, colors[colorIndex])
                stars.add(star)
            }
        }
    }

    var planets = ArrayList<Planet>()
    var planetRings = ArrayList<PlanetRing>()
    fun initPlanet(canvas: Canvas) {
        planets.clear()
        planetRings.clear()
        var x1 = canvas.width * 0.8f
        var y1 = canvas.height * 0.7f
        var radius1 = ScreenUtil.dp2pxF(mContext!!, 23f)
        var colors1 = intArrayOf(Color.parseColor("#FFAA82"), Color.parseColor("#F35005"))
        var shader1 = LinearGradient(
            x1 - radius1,
            y1 - radius1,
            x1 + radius1,
            y1 + radius1,
            colors1,
            floatArrayOf(0f, 1f),
            Shader.TileMode.CLAMP
        )
        var planet1 = Planet(x1, y1, radius1, shader1)
        planets.add(planet1)


        var x2 = canvas.width * 0.65f
        var y2 = canvas.height * 0.65f
        var radius2 = ScreenUtil.dp2pxF(mContext!!, 16f)
        var colors2 = intArrayOf(Color.parseColor("#04A9ED"), Color.parseColor("#092271"))
        var shader2 = LinearGradient(
            x2 - radius2,
            y2 - radius2,
            x2 + radius2,
            y2 + radius2,
            colors2,
            floatArrayOf(0f, 1f),
            Shader.TileMode.CLAMP
        )
        var planet2 = Planet(x2, y2, radius2, shader2)
        planets.add(planet2)


        var x3 = canvas.width * 0.2f
        var y3 = canvas.height * 0.85f
        var radius3 = ScreenUtil.dp2pxF(mContext!!, 36f)
        var radiusRingX3 = ScreenUtil.dp2pxF(mContext!!, 56f)
        var radiusRingY3 = ScreenUtil.dp2pxF(mContext!!, 20f)
        var colors3 = intArrayOf(Color.parseColor("#FF63BFFF"), Color.parseColor("#FF6B88FE"))
        var shader3 = LinearGradient(
            x3 - radius3,
            y3 - radius3,
            x3 + radius3,
            y3 + radius3,
            colors3,
            floatArrayOf(0f, 1f),
            Shader.TileMode.CLAMP
        )
        var planet3 = PlanetRing(
            x3, y3, radius3, shader3, radiusRingX3, radiusRingY3, Color.parseColor("#FF64B6FE")
        )
        planetRings.add(planet3)
    }

    var meteors = ArrayList<Meteor>()
    fun initMeteor(canvas: Canvas) {
        meteors.clear()
        var count = random.nextInt(3) + 5
        var max = ScreenUtil.dp2px(mContext!!, 130f)
        var min = ScreenUtil.dp2px(mContext!!, 60f)
        (0 until count).forEach {

            var length = (random.nextInt(min) + (max - min)).toFloat()
            var meteor = Meteor(
                0f, 0f, length
            )
            meteor.restore(canvas)
            meteors.add(meteor)
        }

    }

    var planetBig: PlanetBig? = null
    fun initBigPlanet(canvas: Canvas) {
        var ringPaint = Paint()
        ringPaint.isAntiAlias = true
        ringPaint.strokeWidth = ScreenUtil.dp2pxF(mContext!!, 2f)
        ringPaint.color = Color.parseColor("#FF037EB4")
        ringPaint.style = Paint.Style.STROKE
        var x = canvas.width / 2
        var y = canvas.height / 2
        var rings = ArrayList<PlanetBig.PlanetBigRing>()
        var planetBigBitmap =
            DrawableUtil.getDrawableToBitmap(mContext!!, R.drawable.planet_big, 0, 0.8f)
        planetBig = PlanetBig(x.toFloat(), y.toFloat(), planetBigBitmap!!, rings, 20f, ringPaint)
        planetBig!!.culTopHalfPath()
        var ringDefaultHeight = ScreenUtil.dp2pxF(mContext!!, 22f)
        var ringDefaultWidth = ScreenUtil.dp2pxF(mContext!!, 80f)
        var ringDeltaHeight = ScreenUtil.dp2pxF(mContext!!, 10f)
        var ringDeltaWidth = ScreenUtil.dp2pxF(mContext!!, 30f)

        var x2 = 0f
        var y2 = 0f
        var radius2 = ScreenUtil.dp2pxF(mContext!!, 6f)
        var colors2 = intArrayOf(Color.parseColor("#FFFFD4FF"), Color.parseColor("#FFFF68A1"))
        var shader2 = LinearGradient(
            x2 - radius2,
            y2 - radius2,
            x2 + radius2,
            y2 + radius2,
            colors2,
            floatArrayOf(0f, 1f),
            Shader.TileMode.CLAMP
        )
        var subPlanet2 = Planet(x2, y2, radius2, shader2)

        var ring1 = PlanetBig.PlanetBigRing(ringDefaultHeight, ringDefaultWidth, subPlanet2)


        var ring2 = PlanetBig.PlanetBigRing(
            ringDefaultHeight + ringDeltaHeight, ringDefaultWidth + ringDeltaWidth, null
        )

        var x3 = 0f
        var y3 = 0f
        var radius3 = ScreenUtil.dp2pxF(mContext!!, 16f)
        var colors3 = intArrayOf(Color.parseColor("#FF9F9FFF"), Color.parseColor("#FFDB8EFF"))
        var shader3 = LinearGradient(
            x3 - radius3,
            y3 - radius3,
            x3 + radius3,
            y3 + radius3,
            colors3,
            floatArrayOf(0f, 1f),
            Shader.TileMode.CLAMP
        )
        var subPlanet3 = Planet(x3, y3, radius3, shader3)

        var ring3 = PlanetBig.PlanetBigRing(
            ringDefaultHeight + ringDeltaHeight * 2,
            ringDefaultWidth + ringDeltaWidth * 2,
            subPlanet3
        )

        rings.add(ring1)
        rings.add(ring2)
        rings.add(ring3)
    }

    override fun doDraw() {
        LogUtil.d("doDraw")

        mainHandler.postDelayed({
            var canvas = lockCanvas() ?: return@postDelayed
//            canvas.drawColor(Color.BLACK)

            //画背景
            var shader = LinearGradient(
                canvas.width / 2f,
                0f,
                canvas.width / 2f,
                canvas.height.toFloat(),
                intArrayOf(backColorStart, backColorCenter, backColorStart),
                floatArrayOf(0f, 0.5f, 1f),
                Shader.TileMode.CLAMP
            )
            backPaint.shader = shader
            canvas.drawRect(Rect(0, 0, canvas.width, canvas.height), backPaint)

            //画流星
            canvas.save()
            canvas.rotate(-30f, (canvas.width / 2).toFloat(), (canvas.height / 2).toFloat())
            meteors.forEach {
                it.draw(canvas, meteorPaint)
            }
            canvas.restore()

            //画星星
            stars.forEach {
                starPaint.color = it.color
                starPaint.alpha = it.alpha
                canvas.drawCircle(it.x, it.y, it.radius, starPaint)
                it.next()
            }


            //画旁边的星球
            planets.forEach {
                planetPaint.shader = it.shader
                canvas.drawCircle(it.x, it.y, it.radius, planetPaint)
            }


            //画旁边星球的圆环
            planetRings.forEach {
                planetPaint.shader = it.shader
                canvas.drawCircle(it.x, it.y, it.radius, planetPaint)
                var ring = RectF(
                    it.x - it.radiusRingX,
                    it.y - it.radiusRingY,
                    it.x + it.radiusRingX,
                    it.y + it.radiusRingY
                )
                ringPaint.color = it.colorRing
                canvas.save()
                canvas.rotate(-30f, it.x, it.y)
                canvas.drawOval(ring, ringPaint)
                canvas.restore()
                var circleRectf =
                    RectF(it.x - it.radius, it.y - it.radius, it.x + it.radius, it.y + it.radius)
                canvas.drawArc(circleRectf, 150f, 180f, false, planetPaint)
            }

            //画大球
            planetBig?.let {
                var top = it.y - it.bitmap.height / 2
                var left = it.x - it.bitmap.width / 2

                canvas.drawCircle(it.x, it.y, it.bitmap.width / 2f, bigPlanetPaint)

                canvas.drawBitmap(it.bitmap, left, top, planetPaint)

                //大球圆环
                canvas.save()
                canvas.rotate(-it.ringDegree, it.x, it.y)
                it.sortRings().forEach { ring ->
                    ring.draw(canvas, it.x, it.y, it.paint, planetPaint)
                }
                canvas.restore()
                //这里画上半部的圆球
                canvas.save()
                canvas.clipPath(it.halfPath)
                canvas.drawBitmap(it.bitmap, left, top, planetPaint)
                canvas.restore()
            }
            degree++

            unlockCanvasAndPost(canvas)
            doDraw()
        }, refreshTime)
    }

    override fun newConfigFragment(): Fragment {
        return Fragment()
    }
}