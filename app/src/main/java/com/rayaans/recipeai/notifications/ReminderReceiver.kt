package com.rayaans.recipeai.notifications

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.annotation.RequiresPermission

// AlarmManager trigger BroadcastReceiver for reminders
class ReminderReceiver: BroadcastReceiver() {
    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override fun onReceive(context: Context, intent: Intent) {
        println("Reminder received")
        val recipeTitle = intent.getStringExtra("recipeTitle") ?: "Recipe"

        NotificationHelper.showNotification(context, "Recipe Reminder",
            "Time to make $recipeTitle")
    }
}