package com.app.testtaskrollncode

import android.content.Context
import android.content.SharedPreferences


class SharedPref (ctx: Context) {

  private val pref: SharedPreferences = ctx.getSharedPreferences("pref", 0)

  fun put(key: String, value: String) {
    val editor = pref.edit()
    editor.putString(key, value)
    editor.apply()
  }

  fun get(key: String): String? {
    return pref.getString(key, null)
  }
}