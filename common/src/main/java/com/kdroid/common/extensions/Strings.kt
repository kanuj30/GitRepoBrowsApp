package com.kdroid.common.extensions

import android.content.Intent
import android.os.Bundle
import java.util.*

fun Intent?.toDeepString(): String {
    return if (this == null) {
        "null"
    } else {
        toString() + " " + extras.toDeepString()
    }
}

fun Bundle?.toDeepString(): String {
    val out = StringBuilder("Bundle[")
    if (this == null) {
        out.append("null")
    } else {
        var first = true
        for (key in this.keySet()) {
            if (!first) {
                out.append(", ")
            }
            out.append(key).append('=')
            when (val value = this[key]) {
                is IntArray -> {
                    out.append(Arrays.toString(value))
                }
                is ByteArray -> {
                    out.append(Arrays.toString(value))
                }
                is BooleanArray -> {
                    out.append(Arrays.toString(value))
                }
                is ShortArray -> {
                    out.append(Arrays.toString(value))
                }
                is LongArray -> {
                    out.append(Arrays.toString(value))
                }
                is FloatArray -> {
                    out.append(Arrays.toString(value))
                }
                is DoubleArray -> {
                    out.append(Arrays.toString(value))
                }
                is Array<*> -> {
                    out.append(Arrays.toString(value))
                }
                is Bundle -> {
                    out.append(value.toDeepString())
                }
                else -> {
                    out.append(value)
                }
            }
            first = false
        }
    }
    out.append("]")
    return out.toString()
}

fun String?.nullIfBlank(): String? {
    return if (this.isNullOrBlank()) null else this
}