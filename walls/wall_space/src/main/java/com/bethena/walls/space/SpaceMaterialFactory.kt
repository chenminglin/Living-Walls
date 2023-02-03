package com.bethena.walls.space

import android.content.Context
import android.graphics.*
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bethena.base_wall.BaseMaterialFactory
import com.bethena.base_wall.utils.DrawableUtil
import com.bethena.base_wall.utils.RandomUtil
import com.bethena.base_wall.utils.ScreenUtil


class SpaceMaterialFactory : BaseMaterialFactory() {
    var starPaint = Paint()

    var backPaint = Paint()
    var ringPaint = Paint()

    var backColorStart = 0
    var backColorCenter = 0

    var planetPaint = Paint()
    var bigPlanetPaint = Paint()

    var meteorPaint = Paint()

    var stars = ArrayList<Star>()

    var planets = ArrayList<Planet>()
    var planetRings = ArrayList<PlanetRing>()

    var meteors = ArrayList<Meteor>()
    var planetBig: PlanetBig? = null

    lateinit var sensorManager: SensorManager
    lateinit var sensor: Sensor

    override fun provider(context: Context, canvas: Canvas, ratePer: Float) {
        starPaint.isAntiAlias = true
        starPaint.color = Color.WHITE
        starPaint.strokeWidth = ScreenUtil.dp2pxF(context, 6f)
        starPaint.style = Paint.Style.FILL

        planetPaint.isAntiAlias = true
        ringPaint.isAntiAlias = true
        ringPaint.style = Paint.Style.STROKE
        ringPaint.strokeWidth = ScreenUtil.dp2pxF(context, 2f)

        bigPlanetPaint.isAntiAlias = true
        bigPlanetPaint.color = Color.parseColor("#FF04A9ED")
        var maskFilter = BlurMaskFilter(100f, BlurMaskFilter.Blur.SOLID)
        bigPlanetPaint.maskFilter = maskFilter

        meteorPaint.isAntiAlias = true
        meteorPaint.style = Paint.Style.STROKE
        meteorPaint.strokeWidth = ScreenUtil.dp2pxF(context, 2.4f)
        meteorPaint.color = Color.WHITE
        meteorPaint.strokeCap = Paint.Cap.ROUND

        backColorStart = ContextCompat.getColor(context, R.color.background_color_start)
        backColorCenter = ContextCompat.getColor(context, R.color.background_color_center)

        initStars(context, canvas, ratePer)
        initPlanet(context, canvas, ratePer)
        initMeteor(context, canvas, ratePer)
        initBigPlanet(context, canvas, ratePer)

        sensorManager = context.getSystemService(AppCompatActivity.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
//        sensorManager.registerListener(sensorListener, sensor, SensorManager.SENSOR_DELAY_FASTEST)


    }


    private fun initStars(context: Context, canvas: Canvas, ratePer: Float) {
        stars.clear()
        var widht = canvas.width
        var height = canvas.height
//        var starCount = random.nextInt(10) + 5
        var MAX_RADIUS = ScreenUtil.dp2px(context, 3f)
        var WIDTH_COUNT = 4
        var HEIGHT_COUNT = 4
        var each_width = (widht - 2 * MAX_RADIUS) / WIDTH_COUNT
        var each_height = (height * 0.4f - 2 * MAX_RADIUS) / HEIGHT_COUNT
        var colors = intArrayOf(Color.parseColor("#FFF7B500"), Color.WHITE)
        (0 until WIDTH_COUNT).forEach { x_d ->
            (0 until HEIGHT_COUNT).forEach { y_d ->
                var x = RandomUtil.nextInt(each_width) + x_d * each_width + MAX_RADIUS
                var y = RandomUtil.nextInt(each_height.toInt()) + y_d * each_height + MAX_RADIUS
                var radius = (RandomUtil.nextFloat() + 0.3f) * MAX_RADIUS
                var alpha = RandomUtil.nextInt(150) + 105
                var colorIndex = RandomUtil.nextInt(2)
                var star = Star(x.toFloat(), y, radius, alpha, colors[colorIndex])
                star.alphaIncreate = (RandomUtil.nextInt(3) + 1) * ratePer
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
                var x = RandomUtil.nextInt(each_width2) + x_d * each_width2 + MAX_RADIUS
                var y =
                    RandomUtil.nextInt(each_height2.toInt()) + y_d * each_height2 + MAX_RADIUS + nextStarY
                var radius = (RandomUtil.nextFloat() + 0.3f) * MAX_RADIUS
                var alpha = RandomUtil.nextInt(150) + 105
                var colorIndex = RandomUtil.nextInt(2)
                var star = Star(x.toFloat(), y, radius, alpha, colors[colorIndex])
                star.alphaIncreate = (RandomUtil.nextInt(3) + 1) * ratePer
                stars.add(star)
            }
        }
    }

    private fun initPlanet(context: Context, canvas: Canvas, ratePer: Float) {
        planets.clear()
        planetRings.clear()
        var x1 = canvas.width * 0.8f
        var y1 = canvas.height * 0.7f
        var radius1 = ScreenUtil.dp2pxF(context, 23f)
        var colors1 = intArrayOf(Color.parseColor("#FFAA82"), Color.parseColor("#F35005"))

        var planet1 = Planet(x1, y1, radius1, colors1)
        planets.add(planet1)


        var x2 = canvas.width * 0.65f
        var y2 = canvas.height * 0.65f
        var radius2 = ScreenUtil.dp2pxF(context, 16f)
        var colors2 = intArrayOf(Color.parseColor("#04A9ED"), Color.parseColor("#092271"))

        var planet2 = Planet(x2, y2, radius2, colors2)
        planets.add(planet2)


        var x3 = canvas.width * 0.2f
        var y3 = canvas.height * 0.85f
        var radius3 = ScreenUtil.dp2pxF(context, 36f)
        var radiusRingX3 = ScreenUtil.dp2pxF(context, 56f)
        var radiusRingY3 = ScreenUtil.dp2pxF(context, 20f)
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

    private fun initMeteor(context: Context, canvas: Canvas, ratePer: Float) {
        meteors.clear()
        var count = RandomUtil.nextInt(3) + 5
        var max = ScreenUtil.dp2px(context, 130f)
        var min = ScreenUtil.dp2px(context, 60f)
        (0 until count).forEach {
            var length = (RandomUtil.nextInt(min) + (max - min)).toFloat()
            var meteor = Meteor(
                0f, 0f, length
            )
            meteor.increateX = ScreenUtil.dp2px(context, 6f) * ratePer
            meteor.restore(canvas)
            meteors.add(meteor)
        }

    }

    private fun initBigPlanet(context: Context, canvas: Canvas, ratePer: Float) {
        var ringPaint = Paint()
        ringPaint.isAntiAlias = true
        ringPaint.strokeWidth = ScreenUtil.dp2pxF(context, 2f)
        ringPaint.color = Color.parseColor("#FF037EB4")
        ringPaint.style = Paint.Style.STROKE
        var x = canvas.width / 2
        var y = canvas.height / 2
        var rings = ArrayList<PlanetBig.PlanetBigRing>()
        var planetBigBitmap =
            DrawableUtil.getDrawableToBitmap(context, R.drawable.planet_big, 0, 0.8f)
        planetBig = PlanetBig(x.toFloat(), y.toFloat(), planetBigBitmap!!, rings, 20f, ringPaint)
        planetBig!!.culTopHalfPath()
        var ringDefaultHeight = ScreenUtil.dp2pxF(context, 22f)
        var ringDefaultWidth = ScreenUtil.dp2pxF(context, 80f)
        var ringDeltaHeight = ScreenUtil.dp2pxF(context, 10f)
        var ringDeltaWidth = ScreenUtil.dp2pxF(context, 30f)

        var x2 = 0f
        var y2 = 0f
        var radius2 = ScreenUtil.dp2pxF(context, 6f)
        var colors2 = intArrayOf(Color.parseColor("#FFFFD4FF"), Color.parseColor("#FFFF68A1"))

        var subPlanet2 = Planet(x2, y2, radius2, colors2)

        var ring1 = PlanetBig.PlanetBigRing(ringDefaultHeight, ringDefaultWidth, subPlanet2)
        ring1.subDegreeIncreate = 1 * ratePer

        var ring2 = PlanetBig.PlanetBigRing(
            ringDefaultHeight + ringDeltaHeight, ringDefaultWidth + ringDeltaWidth, null
        )

        var x3 = 0f
        var y3 = 0f
        var radius3 = ScreenUtil.dp2pxF(context, 16f)
        var colors3 = intArrayOf(Color.parseColor("#FF9F9FFF"), Color.parseColor("#FFDB8EFF"))

        var subPlanet3 = Planet(x3, y3, radius3, colors3)

        var ring3 = PlanetBig.PlanetBigRing(
            ringDefaultHeight + ringDeltaHeight * 2,
            ringDefaultWidth + ringDeltaWidth * 2,
            subPlanet3
        )
        ring3.subDegreeIncreate = 2 * ratePer
        rings.add(ring1)
        rings.add(ring2)
        rings.add(ring3)
    }


    override fun doDraw(context: Context, canvas: Canvas) {
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
            it.next()
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
    }

    var initSensorX = 0f
    var initSensorY = 0f
    var initSensorZ = 0f
    var isInitSensorValue = false

    var sensorListener: SensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent?) {
            event?.let {
//                var newValues = FloatArray(it.values.size)
//                SensorUtil.lowPassAcceleration(it.values, newValues)
                var x = it.values[0]
                var y = it.values[1]
                var z = it.values[2]

                if (!isInitSensorValue) {
                    initSensorX = x
                    initSensorY = y
                    initSensorZ = z
                    isInitSensorValue = true
                } else {
                    var deltaX = x - initSensorX
                    planets.forEach {
                        it.afterSensorX = it.initX + deltaX * 10
                    }

                    planetRings.forEach {
                        it.x = it.initX + deltaX
                    }

                }
            }
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        }

    }

    override fun destory() {
//        sensorManager.unregisterListener(sensorListener)
    }

}