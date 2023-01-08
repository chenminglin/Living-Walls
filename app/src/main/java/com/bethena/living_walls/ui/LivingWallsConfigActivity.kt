package com.bethena.living_walls.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bethena.living_walls.R
import com.bethena.living_walls.log.LogUtils

class LivingWallsConfigActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LogUtils.d("LivingWallsConfigActivity onCreate-----")
        setContentView(R.layout.activity_living_walls_config)
    }
}