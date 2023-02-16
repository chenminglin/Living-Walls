package com.bethena.base_wall.utils

import android.util.Log
import com.bethena.base_wall.BuildConfig

object LogUtil {
    val TAG = "LogUtils"
    val DEBUG = BuildConfig.DEBUG

    fun d(tag: String, vararg msg: Any?) {
        if (DEBUG) {
            var a = ""
            for (m in msg) {
                a += m.toString()
            }
            Log.d(tag, a)
        }
    }

    fun d(msg: String) {
        if (DEBUG) {
            d(TAG, msg)
        }
    }

    fun e(msg: String) {
        if (DEBUG) {
            e(TAG, msg)
        }
    }

    fun e(tag: String, vararg msg: Any?) {
        if (DEBUG) {
            var a = ""
            msg.forEach {
                a + it.toString()
            }
            Log.e(tag, a)
        }
    }
}