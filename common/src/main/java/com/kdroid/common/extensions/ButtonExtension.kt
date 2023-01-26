package com.kdroid.common.extensions

import android.widget.Button

/**
 * Button enabling/disabling modifiers
 */

fun Button.disableButton() {
    isEnabled = false
    alpha = 0.7f
}

fun Button.enableButton() {
    isEnabled = true
    alpha = 1.0f
}