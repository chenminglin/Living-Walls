package com.bethena.base_wall

import android.content.Context

abstract class BaseWallModule {
     var wallInfo: WallInfo? = null
         get() {
             return field
         }

    abstract fun init(context: Context): BaseWallModule

}