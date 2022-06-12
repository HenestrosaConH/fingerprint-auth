package com.henestrosaconh.fingerprintauth.utils

import android.content.Context

object PreferencesUtils {

    private const val NAME = "com.henestrosaconh.fingerpintauth"

    fun put(context: Context, key: String, value: Any) {
        val editor = context.getSharedPreferences(NAME, Context.MODE_PRIVATE).edit()

        when (value) {
            is String -> editor.putString(key, value)
            is Int -> editor.putInt(key, value)
            is Boolean -> editor.putBoolean(key, value)
            is Float -> editor.putFloat(key, value)
            is Long -> editor.putLong(key, value)
        }

        editor.apply()
    }

    fun get(context: Context, key: String, defaultValue: Any): Any? {
        val sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE)

        when (defaultValue) {
            is String -> return sp.getString(key, defaultValue.toString())
            is Int -> return sp.getInt(key, defaultValue.toInt())
            is Boolean -> return sp.getBoolean(key, defaultValue.toString().toBoolean())
            is Float -> return sp.getFloat(key, defaultValue.toFloat())
            is Long -> return sp.getLong(key, defaultValue.toLong())
        }

        return null
    }

}