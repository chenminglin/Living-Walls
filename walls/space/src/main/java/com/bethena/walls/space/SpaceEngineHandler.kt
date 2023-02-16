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
            if (factory == null) {
                factory = SpaceMaterialFactory()
            }
            var ratePer = refreshTime / baseRefreshTime.toFloat()
            factory?.colorTempId =
                spUtils.getString(SpaceConst.KEY_COLOR_TEMP, SpaceConst.COLOR_TEMP_ID_1)
            factory?.provider(it, canvas!!, ratePer)
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
            canvas.drawColor(mashColor)
            unlockCanvasAndPost(canvas)
            doDraw()
        }, refreshTime)
    }


    fun changeColorTemp(colorTemp: ColorTemp) {
        spUtils.putString(SpaceConst.KEY_COLOR_TEMP, colorTemp.tempId)
        factory?.colorTempId = colorTemp.tempId
        restart()
    }


    override fun getMashValue(): Int {
        return spUtils.getInt(SpaceConst.KEY_MASH_PERCENT, 0)
    }

    override fun newConfigFragment(): Fragment {
        return SpaceConfigFragment()
    }

    override fun onDestroy() {
        super.onDestroy()
        factory?.destory()
    }
}