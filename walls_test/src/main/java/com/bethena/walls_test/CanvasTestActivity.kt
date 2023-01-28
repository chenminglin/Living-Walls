package com.bethena.walls_test

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.drawable.VectorDrawable
import android.os.Bundle
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.bethena.base_wall.utils.DrawableUtil


class CanvasTestActivity : AppCompatActivity() {
//    var wallEngineHandler: BaseEngineHandler? = null

    lateinit var toolbar: Toolbar
    lateinit var surfaceView: SurfaceView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        toolbar.title = " "

        setSupportActionBar(toolbar)
//        setSupportActionBar()

        var cloud = ContextCompat.getDrawable(this, R.drawable.cloud)
        if (cloud is VectorDrawable) {

        }



        surfaceView = findViewById(R.id.surfaceView)
//        wallEngineHandler = CircleLivingWallEngineHandler(this)
        surfaceView.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
                var canvas = holder.lockHardwareCanvas()
                doDraw(canvas)
                holder.unlockCanvasAndPost(canvas)
            }

            override fun surfaceChanged(
                holder: SurfaceHolder, format: Int, width: Int, height: Int
            ) {

            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
            }

        })
    }

    var paint = Paint()

    fun doDraw(canvas: Canvas) {
        paint.color = Color.WHITE
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 10f
        canvas.drawColor(Color.BLACK)
        var path = Path()
//        path.lineTo(400f, 400f)
        path.moveTo(100f, 100f)
        path.rQuadTo(700f, 100f, 600f, 600f)
        canvas.drawPath(path, paint)


        var bitmapCloud = DrawableUtil.getDrawableToBitmap(this, R.drawable.cloud)
        var bitmapPlanet = DrawableUtil.getDrawableToBitmap(this, R.drawable.planet)


        canvas.drawBitmap(bitmapCloud!!, 0f, 0f, paint)
        canvas.drawBitmap(bitmapPlanet!!, 400f, 800f, paint)

    }


}