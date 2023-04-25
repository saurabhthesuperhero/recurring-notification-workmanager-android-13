package com.developersmarket.recurringnotification.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import java.util.Calendar
import java.util.concurrent.TimeUnit

fun scheduleDailyAlarms(context: Context, times: List<String>) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    for (time in times) {
        val (hour, minute) = timeTo24HourFormat(time)
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
        }

        val requestCode = hour * 100 + minute // Unique request code
        val alarmIntent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("time", time)
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            requestCode,
            alarmIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )
    }
}

fun timeTo24HourFormat(time: String): Pair<Int, Int> {
    val parts = time.split(":")
    val hour = parts[0].toInt()
    val minute = parts[1].toInt()
    return Pair(hour, minute)
}
