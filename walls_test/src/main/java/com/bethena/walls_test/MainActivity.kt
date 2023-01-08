package com.bethena.walls_test

import android.os.Bundle
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.appcompat.app.AppCompatActivity
import com.bethena.walls.circle.CircleLivingWallEngineHandler

class MainActivity : AppCompatActivity() {
    var circleLivingWallEngineHandler: CircleLivingWallEngineHandler? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        var surfaceView = findViewById<SurfaceView>(R.id.surfaceView)
        circleLivingWallEngineHandler = CircleLivingWallEngineHandler(this)

        surfaceView.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
                circleLivingWallEngineHandler?.onCreate(holder)
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
    }

    override fun onResume() {
        super.onResume()
        window.decorView.post {
            circleLivingWallEngineHandler?.onVisibilityChanged(true)
        }
    }

    override fun onPause() {
        super.onPause()
        circleLivingWallEngineHandler?.onVisibilityChanged(false)
    }
}