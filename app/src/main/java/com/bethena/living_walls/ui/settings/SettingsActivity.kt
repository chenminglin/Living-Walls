package com.bethena.living_walls.ui.settings

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.bethena.base_wall.BaseActivity
import com.bethena.living_walls.R

class SettingsActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        var toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, SettingsFragment())
                .commitAllowingStateLoss()
        }
    }

    companion object {
        fun star(context: Context) {
            context.startActivity(Intent(context, SettingsActivity::class.java))
        }
    }
}