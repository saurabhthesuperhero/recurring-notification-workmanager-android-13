package com.developersmarket.recurringnotification.notification

import android.content.Context

private const val TIMES_PREF_KEY = "times"
private const val TIMES_PREF_FILE = "times_pref"

fun storeTimes(context: Context, times: List<String>) {
    val sharedPreferences = context.getSharedPreferences(TIMES_PREF_FILE, Context.MODE_PRIVATE)
    val timesSet = times.toSet()
    with(sharedPreferences.edit()) {
        putStringSet(TIMES_PREF_KEY, timesSet)
        apply()
    }
}

fun readStoredTimes(context: Context): List<String> {
    val sharedPreferences = context.getSharedPreferences(TIMES_PREF_FILE, Context.MODE_PRIVATE)
    val timesSet = sharedPreferences.getStringSet(TIMES_PREF_KEY, emptySet())
    return timesSet?.toList() ?: emptyList()
}
