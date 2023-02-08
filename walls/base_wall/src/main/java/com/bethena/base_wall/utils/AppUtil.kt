package com.bethena.base_wall.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.core.content.ContextCompat
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

object AppUtil {
    fun getBitmapFromAsset(context: Context, strName: String): Bitmap? {
        val assetManager = context.assets
        return try {
            val istr = assetManager.open(strName)
            BitmapFactory.decodeStream(istr)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        } finally {
//            assetManager.close()
        }
    }

    fun getTextFromAsset(context: Context, strName: String): String {
        val assetManager = context.assets
        var result = StringBuilder()
        var input: InputStream? = null
        var buffer: BufferedReader? = null
        try {
            input = assetManager.open(strName)
            buffer = BufferedReader(InputStreamReader(input))
            var line: String? = buffer.readLine()
            while (line != null) {
                result.append("$line\n")
                line = buffer.readLine()
            }
        } catch (e: Exception) {

        } finally {
            try {
                input?.close()
            } catch (e: Exception) {
            }

            try {
                buffer?.close()
            } catch (e: Exception) {
            }
        }
        return result.toString()

    }

    fun getColorValueByResName(context: Context, resName: String): Int {
        var colorResId = context.resources.getIdentifier(resName, "color", context.packageName)
        return ContextCompat.getColor(context, colorResId)
    }
}