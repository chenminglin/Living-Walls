package com.bethena.walls.circle

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.service.wallpaper.WallpaperService
import android.view.SurfaceHolder
import androidx.fragment.app.Fragment
import com.bethena.base_wall.BaseEngineHandler
import com.bethena.base_wall.utils.LogUtil
import com.bethena.base_wall.utils.ScreenUtil
import kotlin.random.Random

class CircleLivingWallEngineHandler(context: Context?) : BaseEngineHandler(context) {
    private var paint = Paint()
    private var mSensorManager: SensorManager? = null
    private var mSensorGravity: Sensor? = null
    private var circlese = arrayListOf<Circle>()
    private var interpolators = InterpolatorUtil.getInterpolators()
    private var mSensorGravityEventListener: SensorEventListener =
        object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                event?.let {
                    val xAcceleration = event.values[0]
                    val yAcceleration = event.values[1]
                    val zAcceleration = event.values[2]

                    LogUtil.d(
                        "xAcceleration = ",
                        xAcceleration,
                        " yAcceleration = ",
                        yAcceleration,
                        " zAcceleration = ",
                        zAcceleration
                    )
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

            }

        }

    override fun initVariableMaterial() {

    }


    override fun surfaceCreated(surfaceHolder: SurfaceHolder?) {
        super.surfaceCreated(surfaceHolder)
        paint.isAntiAlias = true
        paint.color = Color.parseColor("#88ffffff")
        paint.strokeWidth = ScreenUtil.dp2pxF(mContext!!, 3f)
        paint.style = Paint.Style.STROKE
        LogUtil.d("HealingWallEngine onCreate-----")
        mSensorManager =
            mContext?.getSystemService(WallpaperService.SENSOR_SERVICE) as SensorManager
        mSensorGravity = mSensorManager?.getDefaultSensor(Sensor.TYPE_GRAVITY)
    }

    override fun onVisibilityChanged(visible: Boolean) {
        isVisible = visible

        if (visible) {
            mSensorManager?.registerListener(
                mSensorGravityEventListener,
                mSensorGravity,
                SensorManager.SENSOR_DELAY_NORMAL
            )
            initCirclese()
            doDraw()
        } else {
            mainHandler.removeCallbacksAndMessages(null)
            mSensorManager?.unregisterListener(mSensorGravityEventListener)
        }
    }

    override fun pause() {
    }

    override fun newConfigFragment(): Fragment {
        return Fragment()
    }


    override fun doDraw() {
        if (!isVisible) {
            LogUtil.d("doDraw return..")
            return
        }

        if (circlese.size == 0) {
            return
        }
        var canvas = lockCanvas()
        mainHandler.postDelayed(Runnable {
            mSurfaceHolder?.let { it ->
//                LogUtils.d("doDrawing..")

                if (canvas == null) {
                    return@let
                }
                var width = canvas.width
                var height = canvas.height
//                    canvas.drawARGB(0, 0, 0, 0)
                canvas.save()
//                    canvas.
                canvas.drawColor(Color.BLACK)
//                    canvas.drawARGB(0, 0, 0, 0)

//                canvas.rotate(degrees, width / 2f, height / 2f)

//                canvas.drawLine(0f, 0f, width.toFloat(), height.toFloat(), paint)

                circlese.forEach { c ->
                    paint.strokeWidth = c.paintWidth
//                    if (c.radius < 0) {
//                        return@forEach
//                    }
                    canvas.drawCircle(c.centerX, c.centerY, c.radius, paint)
//                    LogUtils.d(c.toString())
                    c.nextStep()
                }
                canvas.restore()
                it.unlockCanvasAndPost(canvas)
                doDraw()
            }
        }, 20)
    }

    override fun getMashValue(): Int {
        TODO("Not yet implemented")
    }


    fun initCirclese() {
        mContext?.apply {
            circlese.clear()
            var startX = 0
            var startY = 0
            var windowWidth = ScreenUtil.getScreenWidth(this)
            var windowHeight = ScreenUtil.getScreenHeight(this)
            var minRadius = ScreenUtil.dp2pxF(this, 0f)
            var maxRadius = ScreenUtil.dp2pxF(this, 25f)
            var margin = ScreenUtil.dp2px(this, 20f)
            var incre_dp_start = ScreenUtil.dp2pxF(this, 0.1f)
            var incre_dp_end = ScreenUtil.dp2pxF(this, 0.3f)
            var incre_dp = Random.nextDouble(incre_dp_start.toDouble(), incre_dp_end.toDouble())

            for (x in startX..windowWidth step (margin + maxRadius * 2).toInt()) {
                for (y in startY..windowHeight step (margin + maxRadius * 2).toInt()) {
                    var radius = Random.nextDouble(minRadius.toDouble(), maxRadius.toDouble())
                    var paintWidth =
                        Random.nextDouble(
                            ScreenUtil.dp2pxF(this, 0.5f).toDouble(),
                            ScreenUtil.dp2pxF(this, 3f).toDouble()
                        )
                    var circle = Circle(
                        x.toFloat(),
                        y.toFloat(),
                        radius.toFloat(),
                        minRadius,
                        maxRadius,
                        incre_dp.toFloat(),
                        paintWidth.toFloat()
                    )
                    circle.interpolator = interpolators[Random.nextInt(interpolators.size)]
                    circlese.add(circle)
                }
            }
//            var radius = RandomUtil.between2numsF(minRadius, maxRadius)
//            var paintWidth =
//                RandomUtil.between2numsF(
//                    ScreenUtil.dp2pxF(this, 0.5f),
//                    ScreenUtil.dp2pxF(this, 3f)
//                )
//            var circle = Circle(
//                windowWidth.toFloat() / 2,
//                windowHeight.toFloat() / 2,
//                radius,
//                minRadius,
//                maxRadius,
//                incre_dp,
//                paintWidth
//            )
//            circle.interpolator = interpolators[RandomUtil.randomInt(interpolators.size)]
//            circlese.add(circle)
        }

        LogUtil.d("init finish")

    }

}