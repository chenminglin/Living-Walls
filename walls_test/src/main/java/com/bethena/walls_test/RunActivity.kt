package com.bethena.walls_test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bethena.walls_test.service.HealingWallService

class RunActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_run)

        findViewById<View>(R.id.button).setOnClickListener {
            HealingWallService.start(this@RunActivity)
        }
    }
}