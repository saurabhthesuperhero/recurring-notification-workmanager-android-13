package com.developersmarket.recurringnotification.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import java.util.Calendar
import java.util.concurrent.TimeUnit

//fun scheduleDailyAlarms(context: Context, times: List<String>) {
//    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//    val interval = AlarmManager.INTERVAL_DAY
//
//    for (time in times) {
//        val (hour, minute) = timeTo24HourFormat(time)
//        val calendar = Calendar.getInstance().apply {
//            set(Calendar.HOUR_OF_DAY, hour)
//            set(Calendar.MINUTE, minute)
//            set(Calendar.SECOND, 0)
//        }
//
//        // Check if the scheduled time has already passed for today, and if so, schedule it for tomorrow.
//        if (calendar.timeInMillis < System.currentTimeMillis()) {
//            calendar.add(Calendar.DAY_OF_YEAR, 1)
//        }
//
//        val requestCode = hour * 100 + minute // Unique request code
//        val alarmIntent = Intent(context, AlarmReceiver::class.java).apply {
//            putExtra("time", time)
//        }
//        val pendingIntent = PendingIntent.getBroadcast(
//            context,
//            requestCode,
//            alarmIntent,
//            PendingIntent.FLAG_IMMUTABLE
//        )
//
////        alarmManager.setRepeating(
////            AlarmManager.RTC_WAKEUP,
////            calendar.timeInMillis,
////            interval,
////            pendingIntent
////        )
//        scheduleNextAlarm(context, time)
//    }
//}

fun scheduleDailyAlarms(context: Context, times: List<String>) {
    for (time in times) {
        val (hour, minute) = timeTo24HourFormat(time)
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
        }

        scheduleNextAlarm(context, time, calendar.timeInMillis)
    }
}


fun scheduleNextAlarm(context: Context, time: String, triggerTime: Long) {
    val (hour, minute) = timeTo24HourFormat(time)
    val requestCode = hour * 100 + minute
    val alarmIntent = Intent(context, AlarmReceiver::class.java).apply {
        putExtra("time", time)
    }
    val pendingIntent = PendingIntent.getBroadcast(
        context,
        requestCode,
        alarmIntent,
        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
    )

    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent)
    } else {
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent)
    }
}




fun timeTo24HourFormat(time: String): Pair<Int, Int> {
    val parts = time.split(":")
    val hour = parts[0].toInt()
    val minute = parts[1].toInt()
    return Pair(hour, minute)
}
