package com.kdroid.common.extensions

import android.os.Build
import java.time.Instant

fun timestamp(): Long {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        Instant.now().toEpochMilli()
    } else {
        System.currentTimeMillis()
    }
}