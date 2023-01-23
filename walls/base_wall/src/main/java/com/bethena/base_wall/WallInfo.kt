package com.bethena.base_wall

import android.graphics.Bitmap
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WallInfo(
    val name: String,
    val assetCover: String,
    val handlerClassName: String,
    val configClassName: String,
    val version: String,
    //是否推荐pad使用
    var isRecommendPad: Boolean = false,
    //耗电情况，1～5，数字越大，耗电越严重
    var power: Int
) :
    Parcelable {
    var coverBitmap: Bitmap? = null
}

