package com.kdroid.gitrepobrowsapp.utils

import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kdroid.gitrepobrowsapp.GitApplication

fun AppCompatActivity.getAppComponent() =
    (this.application as GitApplication).applicationComponent

fun AppCompatActivity.showToast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

fun View.visible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}