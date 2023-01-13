package com.bethena.base_wall

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WallInfo(
    val name: String,
    val assetCover: String,
    val handlerClassName: String,
    val configClassName: String
) :
    Parcelable
