package com.app.testtaskrollncode

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.switchmaterial.SwitchMaterial


class MainActivity : AppCompatActivity() {

  lateinit var intentForService: Intent
  lateinit var pref: SharedPref
  lateinit var switch: SwitchMaterial
  lateinit var lastLaunchTimeTxt: TextView
  lateinit var countValueTxt: TextView

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    setInitView()

    switch.setOnCheckedChangeListener { _, isChecked ->
      if (isChecked) startService(intentForService)
      else stopService(intentForService)
    }

  }

  override fun onResume() {
    super.onResume()
    registerReceiver()
  }

  override fun onPause() {
    super.onPause()
    unregisterReceiver()
  }

  private fun setInitView() {
    switch = findViewById(R.id.switch1)
    lastLaunchTimeTxt = findViewById(R.id.last_launch_time)
    countValueTxt = findViewById(R.id.count_value)
    intentForService = Intent(this, MyService::class.java)
    pref = SharedPref(this)
    setInitValues()
  }

  private fun setInitValues() {
    val time = pref.get(Constants.lastTime)
    val number = pref.get(Constants.number)
    Log.i("TAG", "time: $time")
    time?.let { lastLaunchTimeTxt.text = "Last launch: \n$time" }
    number?.let { countValueTxt.text = "Actual value: \n$number" }
  }

  private val mReceiver: BroadcastReceiver = object : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
      val time = intent.getStringExtra(Constants.lastTime)
      val number = intent.getStringExtra(Constants.number)
      Log.i("TAG", "time: $time")
      if (time != null) lastLaunchTimeTxt.text = "Last launch: \n$time"
      else if (number != null) countValueTxt.text = "Actual value: \n$number"
    }
  }

  private fun registerReceiver() {
    val filter = IntentFilter()
    filter.addAction("android.intent.action.MyBroadcast")
    registerReceiver(mReceiver, filter)
  }

  private fun unregisterReceiver() {
    unregisterReceiver(mReceiver)
  }
}