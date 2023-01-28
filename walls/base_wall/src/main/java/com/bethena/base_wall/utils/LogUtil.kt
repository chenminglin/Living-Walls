package com.bethena.base_wall.utils

import android.util.Log
import com.bethena.base_wall.BuildConfig

object LogUtil {
    val TAG = "LogUtils"
    val DEBUG = BuildConfig.DEBUG

    fun d(tag: String, msg: String) {
        if (DEBUG) {
            Log.d(tag, msg)
        }
    }

    fun d(msg: String) {
        if (DEBUG) {
            Log.d(TAG, msg)
        }
    }

    fun e(msg: String) {
        if (DEBUG) {
            Log.e(TAG, msg)
        }
    }

    fun e(tag: String, msg: String) {
        if (DEBUG) {
            Log.e(tag, msg)
        }
    }
}