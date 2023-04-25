package com.developersmarket.recurringnotification

import android.content.Context
import android.os.Bundle
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.developersmarket.recurringnotification.notification.scheduleDailyAlarms
import com.developersmarket.recurringnotification.notification.storeTimes
import com.developersmarket.recurringnotification.ui.theme.RecurringNotificationTheme

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

@Composable
fun FragDummyScreen() {
    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val times = listOf(
            "12:35:00",
            "12:39:00",
            "12:42:00"
        ) // You can replace this with the user-provided list of times

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




