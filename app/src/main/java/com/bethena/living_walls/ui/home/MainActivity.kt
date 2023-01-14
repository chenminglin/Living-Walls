package com.bethena.living_walls.ui.home

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bethena.base_wall.BaseActivity
import com.bethena.base_wall.WallInfo
import com.bethena.living_walls.App
import com.bethena.living_walls.R
import com.bethena.living_walls.ui.LivingWallsConfigActivity

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var rv_walls = findViewById<RecyclerView>(R.id.rv_walls)
        var walls = arrayListOf<WallInfo>()
        App.wallModules.forEach {
            walls.add(it.wallInfo!!)
        }

        var adapter = HomeAdapter()
        adapter.setOnItemClickListener { adapter, view, position ->
//            var titleView = view.findViewById<View>(R.id.tv_title)
            LivingWallsConfigActivity.start(
                this@MainActivity,
                App.wallModules[position].wallInfo!!,
                view
            )
        }

        adapter.setHeaderView(layoutInflater.inflate(R.layout.header_home, null, false))

        adapter.data = walls

        rv_walls.adapter = adapter

    }
}