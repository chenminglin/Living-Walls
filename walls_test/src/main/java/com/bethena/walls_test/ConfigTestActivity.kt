package com.bethena.walls_test

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class ConfigTestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_config_test)
        var configFragment = TestApp.wallEngineHandler?.newConfigFragment()

        var toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        toolbar.setNavigationOnClickListener {
            finish()
        }
        setSupportActionBar(toolbar)

        if (configFragment != null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, configFragment)
                .commitAllowingStateLoss()
        }


    }
}