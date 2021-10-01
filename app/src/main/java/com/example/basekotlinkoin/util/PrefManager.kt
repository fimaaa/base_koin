package com.example.basekotlinkoin.util

import android.content.Context
import com.example.basekotlinkoin.base.BaseApplication

class PrefManager(val context: Context = BaseApplication.applicationContext()) {

    private val contextMode = Context.MODE_PRIVATE
    private var pref = context.getSharedPreferences("MIH_PREFS", contextMode)
    private var editor = pref.edit()

    var prefLocale: String?
        get() {
            return pref.getString("MIH_USER_LANGUAGE", null)
        }
        set(value) {
            if (value != null) {
                editor.putString("MIH_USER_LANGUAGE", value)?.apply()
            }
        }

    var prefUserToken: String?
        get() {
            return pref.getString("MIH_USER_TOKEN", null)
        }
        set(value) {
            if (value != null) {
                editor.putString("MIH_USER_TOKEN", value)?.apply()
            }
        }

    var prefRefreshToken: String?
        get() {
            return pref.getString("MIH_REFRESH_TOKEN", null)
        }
        set(value) {
            if (value != null) {
                editor.putString("MIH_REFRESH_TOKEN", value)?.apply()
            }
        }

    fun clearPreferences() {
        editor.clear().apply()
    }
}