package com.bethena.walls_test

import android.os.Bundle
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bethena.walls.thunder_breath.ThunderBreathEngineHandler
import com.google.android.material.slider.Slider

class ConfigTestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_config_test)
        var configFragment = TestApp.wallEngineHandler?.newConfigFragment()

        var toolbar = findViewById<Toolbar>(R.id.toolbar)
//        toolbar.setNavigationIcon(R.drawable.ic_round_arrow_back_24)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            finish()
        }
        if (configFragment != null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, configFragment)
                .commitAllowingStateLoss()
        }


    }




}