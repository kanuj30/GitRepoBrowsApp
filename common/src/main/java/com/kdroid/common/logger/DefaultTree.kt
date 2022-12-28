package com.kdroid.common.logger

import timber.log.Timber

/**
 * Creates an element tag which is clickable from Android Studio Logcat window.
 * Forwards caught exceptions to Crashlytics for analysis.
 */
open class DefaultTree : Timber.DebugTree() {

    override fun createStackElementTag(element: StackTraceElement): String {
        return createTag(element, null)
    }

    override fun e(t: Throwable?) {
        super.e(t)
        handle(t)
    }

    override fun e(t: Throwable?, message: String?, vararg args: Any?) {
        super.e(t, message, *args)
        handle(t, message, *args)
    }

    override fun w(t: Throwable?) {
        super.w(t)
        handle(t)
    }

    override fun w(t: Throwable?, message: String?, vararg args: Any?) {
        super.w(t, message, *args)
        handle(t, message, *args)
    }

    private fun handle(t: Throwable?, message: String? = null, vararg args: Any?) {
        if (t == null) return
        forward(t, if (message != null) String.format(message, *args) else null)
    }

    private fun forward(error: Throwable, message: String?) {
//        kotlin.runCatching {
//            FirebaseCrashlytics.getInstance().apply {
//                if (message != null) {
//                    log(message)
//                }
//                recordException(error)
//            }
//        }
    }
}