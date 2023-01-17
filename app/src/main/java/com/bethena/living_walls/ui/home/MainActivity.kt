package com.bethena.living_walls.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import com.bethena.base_wall.BaseActivity
import com.bethena.living_walls.R
import com.bethena.living_walls.ui.settings.SettingsActivity

class MainActivity : BaseActivity() {
    //    lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

//        navController = findNavController(R.id.navFragment)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_setting -> {
                SettingsActivity.star(this)
            }
        }
        return true
    }

    companion object{
        fun start(context: Context){
            var intent = Intent(context,MainActivity::class.java)
            context.startActivity(intent)
        }
    }
}