package com.app.testtaskrollncode

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.app.testtaskrollncode.TimeUtil.currentTime
import java.util.*
import java.util.concurrent.Executors


class MyService internal constructor() : Service() {
  override fun onBind(intent: Intent?): IBinder? {
    return null
  }

  private lateinit var pref: SharedPref
  var i = 0
  private val mTimer = Timer()

  override fun onCreate() {
    super.onCreate()
    pref = SharedPref(this)
  }

  override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
    val currentTime = currentTime()
    mThread.start()
    saveTimeLaunchService(currentTime)
    broadcastIntent(Constants.lastTime, currentTime)
    return super.onStartCommand(intent, flags, startId)
  }

  override fun onDestroy() {
    mTimer.cancel()
    mThread.interrupt()
    super.onDestroy()
  }

  private val mThread = Thread {
    i = getActualValue()
    timer()
  }

  private fun broadcastIntent(key: String, value: String) {
    val broadcastIntent = Intent()
    broadcastIntent.putExtra(key, value)
    broadcastIntent.setClass(this, MyBroadcast::class.java)
    this.sendBroadcast(broadcastIntent)
  }

  private fun timer() {
    mTimer.schedule(object : TimerTask() {
      override fun run() {
        i += 1
        saveActualValue("$i")
        broadcastIntent(Constants.number, "$i")
      }
    }, 0, 5000)
  }

  private fun saveTimeLaunchService(currentTime: String) {
    pref.put(Constants.lastTime, currentTime)
  }

  private fun saveActualValue(number: String) {
    pref.put(Constants.number, number)
  }

  private fun getActualValue(): Int {
    val num = pref.get(Constants.number)
    return num?.toInt() ?: 0
  }
}