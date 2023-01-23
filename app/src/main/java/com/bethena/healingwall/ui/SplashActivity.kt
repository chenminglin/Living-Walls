package com.bethena.healingwall.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.LayerDrawable
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.res.ResourcesCompat
import androidx.preference.PreferenceManager
import com.bethena.base_wall.BaseActivity
import com.bethena.base_wall.utils.LogUtil
import com.bethena.healingwall.R
import com.bethena.healingwall.ui.home.MainActivity

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        LogUtil.d("SplashActivity onCreate")
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            splashScreen.setOnExitAnimationListener { view ->
                val set = AnimatorSet()
                set.playTogether(
                    ObjectAnimator.ofFloat(view, "alpha", 1f),
                    ObjectAnimator.ofFloat(view.iconView, "alpha", 1f)
                )
                set.duration = 500
                set.startDelay = 500

                set.addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(
                        animation: Animator,
                        isReverse: Boolean
                    ) {
                        startMain()
//                        view.remove()
                    }
                })
                set.start()
            }
        } else {
//            initTheme()
//            setContentView(R.layout.activity_splash)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val splashContent = ResourcesCompat.getDrawable(
                    resources, R.drawable.content_splash, theme
                ) as LayerDrawable?
                window.decorView.background = splashContent
                try {
                    var logoAnim =
                        splashContent!!.findDrawableByLayerId(R.id.logo) as AnimatedVectorDrawable
                    logoAnim.start()
                    Handler(Looper.getMainLooper()).postDelayed({ startMain() }, 1000)
                } catch (e: Exception) {
                    startMain()
                }
            } else {
                startMain()
            }
        }
    }

    fun initTheme() {
        var preference = PreferenceManager.getDefaultSharedPreferences(this)
        var themeDefaultValue = getString(R.string.settings_item_theme_defaul)
        var themeKey = getString(R.string.settings_item_theme_key)
        var themeValue = preference.getString(themeKey, themeDefaultValue)
        var themeValues = resources.getStringArray(R.array.settings_theme_values)
        var nightMode = when (themeValue) {
            themeValues[0] -> {
                AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            }
            themeValues[1] -> {
                AppCompatDelegate.MODE_NIGHT_NO
            }
            themeValues[2] -> {
                AppCompatDelegate.MODE_NIGHT_YES
            }
            else -> {
                AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            }
        }
        AppCompatDelegate.setDefaultNightMode(nightMode)
    }

    fun startMain() {
        window.decorView.postDelayed({
            if (isFinishing) {
                return@postDelayed
            }
            MainActivity.start(this)
            finish()
        }, 300)
    }


}