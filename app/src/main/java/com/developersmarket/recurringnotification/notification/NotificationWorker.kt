package com.developersmarket.recurringnotification.notification

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import com.developersmarket.recurringnotification.R

class NotificationWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {
    val context = appContext

    override fun doWork(): Result {
        // Perform your background task, e.g. API call or calculations
        // ...
        val time = inputData.getString("time") ?: ""
        // Show the notification
        showNotification(time, "notification_recurring", context)
        // Return the result
        return Result.success()
    }

    fun createNotificationChannel(channelId: String, context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "notification_recurring"
            val descriptionText = "Your Channel Description"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(channelId, name, importance).apply {
                description = descriptionText
                enableLights(true)
                lightColor = Color.BLUE
                enableVibration(true)
            }

            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun showNotification(time: String, channelId: String, context: Context) {
        createNotificationChannel(channelId, context)
        val notificationId = 100 // Unique notification ID
        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground) // Replace with your app's icon
            .setContentTitle("Your Notification Title")
            .setContentText("Your Notification Text - Time: $time")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDefaults(Notification.DEFAULT_ALL)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(context)) {
            notify(notificationId, builder.build())
        }
    }

}