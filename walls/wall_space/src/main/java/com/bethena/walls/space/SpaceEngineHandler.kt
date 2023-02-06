package com.bethena.walls.space

import android.content.Context
import android.graphics.Canvas
import androidx.fragment.app.Fragment
import com.bethena.base_wall.BaseEngineHandler
import com.bethena.base_wall.utils.LogUtil

class SpaceEngineHandler(context: Context?) : BaseEngineHandler(context) {
    var factory: SpaceMaterialFactory? = null

    override fun initVariableMaterial() {
        mContext?.let {
            var canvas: Canvas? = lockCanvas() ?: return
            factory = SpaceMaterialFactory()
            var ratePer = refreshTime / baseRefreshTime.toFloat()
            factory?.provider(it, canvas!!,ratePer)
            unlockCanvasAndPost(canvas)
        }
    }


    override fun doDraw() {
        LogUtil.d("doDraw refreshTime = $refreshTime")

        mainHandler.postDelayed({
            var canvas = lockCanvas() ?: return@postDelayed
            if (mContext == null) {
                return@postDelayed
            }
            factory?.doDraw(mContext!!, canvas)
            unlockCanvasAndPost(canvas)
            doDraw()
        }, refreshTime)
    }

    override fun newConfigFragment(): Fragment {
        return SpaceConfigFragment()
    }

    override fun onDestroy() {
        super.onDestroy()
        factory?.destory()
    }
}