package com.bethena.base_wall

import android.util.Log

object LogUtils {
    val TAG = "LogUtils"
    fun d(msg: String) {
        Log.d(TAG, msg)
    }

    fun e(msg: String) {
        Log.e(TAG, msg)
    }
}