package com.kdroid.common.extensions

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat

const val DEFAULT_NOTIFICATION_CHANNEL_ID = "low_priority_default"

fun Context.createDefaultNotificationChannel() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            DEFAULT_NOTIFICATION_CHANNEL_ID,
            appLabel,
            NotificationManager.IMPORTANCE_LOW
        )
        val manager = getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel)
    }
}

fun Service.startInForegroundIfNeeded() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val notification =
            NotificationCompat.Builder(this, DEFAULT_NOTIFICATION_CHANNEL_ID)
                .setContentTitle(this.javaClass.name)
                .build()
        startForeground(1, notification)
    }
}
