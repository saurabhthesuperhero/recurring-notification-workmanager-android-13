package com.developersmarket.recurringnotification.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
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
    }
}
