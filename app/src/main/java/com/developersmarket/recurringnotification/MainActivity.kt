package com.developersmarket.recurringnotification

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.PowerManager
import android.provider.Settings
import android.provider.Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.getSystemService
import com.developersmarket.recurringnotification.notification.scheduleDailyAlarms
import com.developersmarket.recurringnotification.notification.storeTimes
import com.developersmarket.recurringnotification.ui.theme.RecurringNotificationTheme
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RecurringNotificationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    FragDummyScreen()
                }
            }
        }
    }
}

fun requestIgnoreBatteryOptimizations(context: Context) {
    val packageName = context.packageName
    val pm = context.getSystemService(Context.POWER_SERVICE) as PowerManager
    if (!pm.isIgnoringBatteryOptimizations(packageName)) {
        val intent = Intent(ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS).apply {
            data = Uri.parse("package:$packageName")
        }
        context.startActivity(intent)
    }

}


fun showBatteryOptimizationInstructions(context: Context) {
    val manufacturer = Build.MANUFACTURER.toLowerCase(Locale.ROOT)
    val message: String = when (manufacturer) {
        "huawei" -> "To ensure our app runs smoothly, please go to Settings > Apps > Your App > Battery > App Launch and disable 'Manage Automatically'."
        "xiaomi" -> "To ensure our app runs smoothly, please go to Settings > Apps > Your App > Battery Saver and select 'No restrictions'."
        "oppo" -> "To ensure our app runs smoothly, please go to Settings > Battery > Your App and enable 'Allow Background Running'."
        else -> "To ensure our app runs smoothly, please disable battery optimization for our app in your device's settings."
    }

    val builder = AlertDialog.Builder(context)
    builder.setTitle("Battery Optimization")
    builder.setMessage(message)
    builder.setPositiveButton("OK") { dialog, _ ->
        run {
            openAppSettings(context)
            dialog.dismiss()
        }
    }
    builder.create().show()
}

fun openAppSettings(context: Context) {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
        data = Uri.fromParts("package", context.packageName, null)
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    context.startActivity(intent)
}

@Composable
fun FragDummyScreen() {
    val context = LocalContext.current
    requestIgnoreBatteryOptimizations(context)
//    showBatteryOptimizationInstructions(context)

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val times = listOf(
            "11:54:00",
            "11:58:00",
            "12:08:00",
            "12:10:00",
            "12:12:00",
        )

        Column(modifier = Modifier.padding(16.dp)) {
            storeTimes(context, times)
            Text(text = "Schedule Alarms")
            Button(onClick = {
                scheduleDailyAlarms(context, times)
            }) {
                Text(text = "Set Alarms")
            }
        }
    }
}




