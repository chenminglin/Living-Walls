package com.bethena.living_walls.ui.home

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bethena.base_wall.BaseFragment
import com.bethena.base_wall.WallInfo
import com.bethena.living_walls.App
import com.bethena.living_walls.R
import com.bethena.living_walls.ui.LivingWallsConfigActivity

class HomeFragment:BaseFragment() {
    override fun layoutId(): Int {
        return R.layout.fragment_home
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var rv_walls = view.findViewById<RecyclerView>(R.id.rv_walls)
        var walls = arrayListOf<WallInfo>()
        App.wallModules.forEach {
            walls.add(it.wallInfo!!)
        }
        var adapter = HomeAdapter()
        adapter.setOnItemClickListener { adapter, view, position ->
//            var titleView = view.findViewById<View>(R.id.tv_title)
            activity?.let {
                LivingWallsConfigActivity.start(
                    it,
                    App.wallModules[position].wallInfo!!,
                    view
                )
            }
        }

        adapter.setHeaderView(layoutInflater.inflate(R.layout.header_home, null, false))
        adapter.data = walls
        rv_walls.adapter = adapter
    }


}