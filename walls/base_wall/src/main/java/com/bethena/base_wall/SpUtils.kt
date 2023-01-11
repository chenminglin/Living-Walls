package com.bethena.base_wall

import android.content.Context
import android.content.SharedPreferences

class SpUtils {
    private var sSharedPreferences: SharedPreferences? = null
    private var sEditor: SharedPreferences.Editor? = null

    constructor(context: Context, spName: String) {
        sSharedPreferences =
            context.getSharedPreferences(spName, Context.MODE_PRIVATE)
        sEditor = sSharedPreferences?.edit()
    }


    fun putInt(key: String?, value: Int) {
        sEditor?.putInt(key, value)
        sEditor?.apply()
    }

    fun getInt(key: String?): Int {
        return getInt(key, 0)
    }

    fun getInt(key: String?, defValue: Int): Int {
        if (sSharedPreferences == null) {
            return 0
        }
        return sSharedPreferences!!.getInt(key, defValue)
    }

    fun putFloat(key: String?, value: Float) {
        sEditor?.putFloat(key, value)
        sEditor?.apply()
    }

    fun getFloat(key: String?): Float {
        return getFloat(key, 0f)
    }

    fun getFloat(key: String?, defValue: Float): Float {
        if (sSharedPreferences == null) {
            return 0f
        }
        return sSharedPreferences!!.getFloat(key, defValue)
    }


    fun putBoolean(key: String?, value: Boolean) {
        sEditor?.putBoolean(key, value)
        sEditor?.apply()
    }

    fun getBoolean(key: String?): Boolean {
        return getBoolean(key, false)
    }

    fun getBoolean(key: String?, defValue: Boolean): Boolean {
        if (sSharedPreferences == null) {
            return false
        }
        return sSharedPreferences!!.getBoolean(key, defValue)
    }


}

