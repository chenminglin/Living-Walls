package com.bethena.living_walls.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bethena.base_wall.BaseActivity
import com.bethena.base_wall.WallInfo
import com.bethena.living_walls.R
import com.bethena.walls.starry_sky.StarrySkyEngineHandler
import com.bethena.walls.starry_sky.StarrySkySettingFragment

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        findViewById<View>(R.id.button).setOnClickListener {
//            var wallInfo = WallInfo("",
//                StarrySkyEngineHandler::class.java.name,
//                StarrySkySettingFragment::class.java.name
//            )
//        }
//        LivingWallsConfigActivity.start(this@MainActivity, wallInfo)
    }
}