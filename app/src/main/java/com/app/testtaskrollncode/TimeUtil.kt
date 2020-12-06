package com.app.testtaskrollncode

import java.text.SimpleDateFormat
import java.util.*

object TimeUtil {

  fun currentTime(): String {
    val c: Date = Calendar.getInstance().time
    val df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    return df.format(c)
  }

}