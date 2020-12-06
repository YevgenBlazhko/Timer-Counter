package com.app.testtaskrollncode

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent


class MyBroadcast : BroadcastReceiver() {
    override fun onReceive(ctx: Context?, p1: Intent?) {
        val time = p1?.getStringExtra(Constants.lastTime)
        val number = p1?.getStringExtra(Constants.number)
        if (time != null) ctx?.sendToActivity(Constants.lastTime, time)
        else if (number != null) ctx?.sendToActivity(Constants.number, number)
    }

    private fun Context.sendToActivity(key: String, value: String?) {
        val i = Intent("android.intent.action.MyBroadcast")
        i.putExtra(key, value)
        this.sendBroadcast(i)
    }
}