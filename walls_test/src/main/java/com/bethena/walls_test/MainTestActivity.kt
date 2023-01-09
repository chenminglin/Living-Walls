package com.bethena.walls_test

import android.os.Bundle
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.bethena.base_wall.BaseEngineHandler
import com.bethena.walls.starry_sky.StarrySkyLivingWallEngineHandler

class MainTestActivity : AppCompatActivity() {
    var wallEngineHandler: BaseEngineHandler? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        var surfaceView = findViewById<SurfaceView>(R.id.surfaceView)
//        wallEngineHandler = CircleLivingWallEngineHandler(this)
        wallEngineHandler = StarrySkyLivingWallEngineHandler(this)

        surfaceView.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
                wallEngineHandler?.onCreate(holder)
            }

            override fun surfaceChanged(
                holder: SurfaceHolder,
                format: Int,
                width: Int,
                height: Int
            ) {
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
            }

        })

        findViewById<Button>(R.id.btn_pause).setOnClickListener {
            wallEngineHandler?.pause()
        }
    }

    override fun onResume() {
        super.onResume()
        window.decorView.post {
            wallEngineHandler?.onVisibilityChanged(true)
        }
    }

    override fun onPause() {
        super.onPause()
        wallEngineHandler?.onVisibilityChanged(false)
    }
}