package com.bethena.living_walls.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bethena.base_wall.BaseFragment
import com.bethena.base_wall.WallInfo
import com.bethena.base_wall.utils.ToastUtil
import com.bethena.living_walls.Const
import com.bethena.living_walls.R
import com.bethena.living_walls.service.LivingWallsService

class LivingWallsConfigActivity : AppCompatActivity() {
    lateinit var fragment: BaseFragment
    var wallInfo:WallInfo? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_living_walls_config)
        var toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        wallInfo = intent.getParcelableExtra(Const.KEY_WALLS)
        if (wallInfo != null) {
            try {
                var fragmentClass = Class.forName(wallInfo!!.configClassName)
                    .asSubclass(BaseFragment::class.java)
                fragment = fragmentClass.newInstance()

                supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commitAllowingStateLoss()
            } catch (e: Exception) {
                ToastUtil.showLong(this, com.bethena.base_wall.R.string.found_error)
                finish()
            }
        } else {
            ToastUtil.showLong(this, com.bethena.base_wall.R.string.found_error)
            finish()
        }

        findViewById<View>(R.id.fab_check).setOnClickListener {
            LivingWallsService.start(this@LivingWallsConfigActivity, wallInfo!!)
        }

    }

    companion object {
        fun start(activity: Activity, wallInfo: WallInfo) {
            var intent = Intent(activity, LivingWallsConfigActivity::class.java)
            intent.putExtra(Const.KEY_WALLS, wallInfo)
            activity.startActivity(intent)
        }
    }
}