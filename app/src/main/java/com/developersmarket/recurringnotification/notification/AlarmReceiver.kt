package com.developersmarket.recurringnotification.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import java.util.Calendar
import java.util.concurrent.TimeUnit

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        // Schedule a one-time WorkManager task to perform the background work
        val time = intent.getStringExtra("time") ?: ""
        val workRequest = OneTimeWorkRequestBuilder<NotificationWorker>()
            .setInputData(workDataOf("time" to time))
            .setInitialDelay(0, TimeUnit.MILLISECONDS)
            .build()

        WorkManager.getInstance(context).enqueue(workRequest)

        // Schedule the next alarm after the current one is triggered
        val (hour, minute) = timeTo24HourFormat(time)
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            add(Calendar.DAY_OF_YEAR, 1) // Schedule the alarm for the same time the next day
        }
        scheduleNextAlarm(context, time, calendar.timeInMillis)
    }

}
