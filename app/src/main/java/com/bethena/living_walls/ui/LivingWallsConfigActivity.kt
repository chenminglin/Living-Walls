package com.bethena.living_walls.ui

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.bethena.base_wall.BaseActivity
import com.bethena.base_wall.BaseFragment
import com.bethena.base_wall.WallInfo
import com.bethena.base_wall.utils.ToastUtil
import com.bethena.living_walls.Const
import com.bethena.living_walls.R
import com.bethena.living_walls.service.LivingWallsService

class LivingWallsConfigActivity : BaseActivity() {
    lateinit var fragment: BaseFragment
    var wallInfo: WallInfo? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_living_walls_config)
        var toolbar = findViewById<Toolbar>(R.id.toolbar)

        // Set up shared element transition
        findViewById<View>(R.id.fragment_container).transitionName = Const.KEY_TRANSITION_VIEW
//        toolbar.title = "1122233444"
        setSupportActionBar(toolbar)

        toolbar.transitionName = Const.KEY_TRANSITION_TITLE_VIEW
        toolbar.setNavigationOnClickListener {
            finish()
        }

        wallInfo = intent.getParcelableExtra(Const.KEY_WALLS)

        if (wallInfo != null) {
            title = wallInfo?.name
            if (savedInstanceState == null) {
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
        fun start(activity: Activity, wallInfo: WallInfo, view: View) {
            var intent = Intent(activity, LivingWallsConfigActivity::class.java)
            var pair1 = android.util.Pair(view, Const.KEY_TRANSITION_VIEW)
//            var pair2 = android.util.Pair(titleView, Const.KEY_TRANSITION_TITLE_VIEW)
            val options = ActivityOptions.makeSceneTransitionAnimation(
                activity, pair1
            )
            intent.putExtra(Const.KEY_WALLS, wallInfo)

            activity.startActivity(intent, options.toBundle())
//            activity.startActivity(intent)
        }
    }
}