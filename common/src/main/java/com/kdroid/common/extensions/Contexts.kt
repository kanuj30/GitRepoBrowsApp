package com.kdroid.common.extensions

import android.content.Context
import android.content.Intent
import android.os.*
import androidx.annotation.ArrayRes
import androidx.annotation.MainThread
import com.kdroid.common.logger.le
import kotlin.random.Random

/**
 * Get app label.
 */
val Context.appLabel get() = applicationInfo.loadLabel(packageManager).toString()

/**
 * Safely start service.
 */
inline fun Context.startService(block: Intent.() -> Unit) {
    try {
        Intent().apply {
            block(this)
            val component = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(this)
            } else {
                startService(this)
            }
            checkNotNull(component) { "could not find service to start; action:${this.action}, component:${this.component}" }
        }
    } catch (t: Throwable) {
        le(t)
    }
}

/**
 * Safely start activity.
 */
inline fun Context.startActivity(block: Intent.() -> Unit) {
    try {
        Intent().apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
            block(this)
            startActivity(this)
        }
    } catch (t: Throwable) {
        le(t)
    }
}

/**
 * Add callback to intent. Uses a Messenger instance which can be used across processes.
 */
@MainThread
fun Intent.putCallback(name: String, callback: (Bundle) -> Unit) {
    putExtra(name, Messenger(Handler {
        callback.invoke(it.data)
        true
    }))
}

/**
 * Extract callback from intent.
 */
fun Intent.getCallback(name: String): ((Bundle) -> Unit)? {
    return try {
        val messenger = getParcelableExtra<Messenger>(name)
        return { bundle -> messenger?.send(Message().apply { data = bundle }) }
    } catch (t: Throwable) {
        null
    }
}

@JvmOverloads
fun Context.random(@ArrayRes arrayRes: Int, random: Random? = null): String {
    return resources.getStringArray(arrayRes)
        .let { if (random != null) it.random(random) else it.random() }
}