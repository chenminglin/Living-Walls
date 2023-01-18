package com.bethena.base_wall

abstract class BaseConfigFragment:BaseFragment() {
    var onWallCheckListener:OnWallCheckListener? = null

    fun checkWall(){
        onWallCheckListener?.onWallCheck()
    }
}