package com.kdroid.common.logger

import timber.log.Timber

/**
 * Although in most instances Timbers use of StackTraceElement is sufficient enough for generating
 * tags, the one case it falls short on is logs generated in base superclasses, which can't retrieve
 * the inheriting class name through StackTraceElement.
 *
 * These extension functions provide a wrapper around timber which generate the proper tag from the
 * current calling class. They can be used in place of or along side Timber.
 */

fun Any.lv(t: Throwable) {
    Timber.tag(generateTagFromInstance()).v(t)
}

fun Any.lv(message: String, vararg args: Any) {
    Timber.tag(generateTagFromInstance()).v(message, *args)
}

fun Any.lv(t: Throwable, message: String, vararg args: Any) {
    Timber.tag(generateTagFromInstance()).v(t, message, *args)
}

fun Any.ld(t: Throwable) {
    Timber.tag(generateTagFromInstance()).d(t)
}

fun Any.ld(message: String, vararg args: Any) {
    Timber.tag(generateTagFromInstance()).d(message, *args)
}

fun Any.ld(t: Throwable, message: String, vararg args: Any) {
    Timber.tag(generateTagFromInstance()).d(t, message, *args)
}

fun Any.li(t: Throwable) {
    Timber.tag(generateTagFromInstance()).i(t)
}

fun Any.li(message: String, vararg args: Any) {
    Timber.tag(generateTagFromInstance()).i(message, *args)
}

fun Any.li(t: Throwable, message: String, vararg args: Any) {
    Timber.tag(generateTagFromInstance()).i(t, message, *args)
}

fun Any.lw(t: Throwable) {
    Timber.tag(generateTagFromInstance()).w(t)
}

fun Any.lw(message: String, vararg args: Any) {
    Timber.tag(generateTagFromInstance()).w(message, *args)
}

fun Any.lw(t: Throwable, message: String, vararg args: Any) {
    Timber.tag(generateTagFromInstance()).w(t, message, *args)
}

fun Any.le(t: Throwable) {
    Timber.tag(generateTagFromInstance()).e(t)
}

fun Any.le(message: String, vararg args: Any) {
    Timber.tag(generateTagFromInstance()).e(message, *args)
}

fun Any.le(t: Throwable, message: String, vararg args: Any) {
    Timber.tag(generateTagFromInstance()).e(t, message, *args)
}

private const val CALL_STACK_INDEX = 2
private fun Any.generateTagFromInstance(): String {
    if (this is CharSequence) {
        return this.toString()
    }
    val stackTrace = Throwable().stackTrace
    check(stackTrace.size >= CALL_STACK_INDEX) { "Synthetic stacktrace didn't have enough elements: are you using proguard?" }
    val element = stackTrace[CALL_STACK_INDEX]
    return createTag(element, this)
}

internal fun createTag(element: StackTraceElement, instance: Any?): String {
    val fileName = element.fileName.substringBefore('.')
    val instanceName = instance?.javaClass?.simpleName
    return if (fileName == instanceName || instanceName == null) {
        "(${element.fileName}:${element.lineNumber})"
    } else {
        "$instanceName (${element.fileName}:${element.lineNumber})"
    }
}