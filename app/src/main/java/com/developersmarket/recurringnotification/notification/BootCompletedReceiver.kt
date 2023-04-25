package com.developersmarket.recurringnotification.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class BootCompletedReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            // Read the stored times from shared preferences or other storage and reschedule the alarms
            val times = readStoredTimes(context)
            scheduleDailyAlarms(context, times)
        }
    }
}
