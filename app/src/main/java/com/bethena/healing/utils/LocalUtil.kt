package com.bethena.healing.utils

import android.content.Context
import androidx.core.os.LocaleListCompat
import androidx.preference.PreferenceManager
import com.bethena.healing.R

object LocalUtil {

    fun getLocal(context: Context): String? {
        var preference = PreferenceManager.getDefaultSharedPreferences(context)
        var lanDefaultValue = context.getString(R.string.settings_item_language_defaul)
        var lanKey = context.getString(R.string.settings_item_language_key)
        var lanValue = preference.getString(lanKey, lanDefaultValue)
        return lanValue
    }

    fun getLocaleListCompat(context: Context): LocaleListCompat {
        var preference = PreferenceManager.getDefaultSharedPreferences(context)
        var lanDefaultValue = context.getString(R.string.settings_item_language_defaul)
        var lanKey = context.getString(R.string.settings_item_language_key)
        var lanValue = preference.getString(lanKey, lanDefaultValue)
        var local = LocaleListCompat.forLanguageTags(lanValue)
        return local
    }
}