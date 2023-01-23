package com.bethena.healingwall.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.bethena.base_wall.BaseActivity
import com.bethena.base_wall.BaseConfigFragment
import com.bethena.base_wall.OnWallCheckListener
import com.bethena.base_wall.WallInfo
import com.bethena.base_wall.utils.ToastUtil
import com.bethena.healingwall.Const
import com.bethena.healingwall.R
import com.bethena.healingwall.service.HealingWallService

class HealingWallConfigActivity : BaseActivity() {
    lateinit var fragment: BaseConfigFragment
    var wallInfo: WallInfo? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_healing_wall_config)
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
                        .asSubclass(BaseConfigFragment::class.java)
                    fragment = fragmentClass.newInstance()
                    fragment.onWallCheckListener = object : OnWallCheckListener {
                        override fun onWallCheck() {
                            HealingWallService.start(this@HealingWallConfigActivity, wallInfo!!)
                            finish()
                        }
                    }
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

    }

    override fun onBackPressed() {
        if (!fragment.onBackPressed()){
            super.onBackPressed()
        }
    }



    companion object {
        fun start(activity: Activity, wallInfo: WallInfo, view: View) {
            var intent = Intent(activity, HealingWallConfigActivity::class.java)
//            var pair1 = android.util.Pair(view, Const.KEY_TRANSITION_VIEW)
////            var pair2 = android.util.Pair(titleView, Const.KEY_TRANSITION_TITLE_VIEW)
//            val options = ActivityOptions.makeSceneTransitionAnimation(
//                activity, pair1
//            )
            intent.putExtra(Const.KEY_WALLS, wallInfo)

            activity.startActivity(intent)
//            activity.startActivity(intent)
        }
    }
}