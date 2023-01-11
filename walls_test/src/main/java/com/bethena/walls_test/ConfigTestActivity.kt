package com.bethena.walls_test

import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class ConfigTestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_config_test)
        var configFragment = TestApp.wallEngineHandler?.newConfigFragment()

        var toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return false
    }





}