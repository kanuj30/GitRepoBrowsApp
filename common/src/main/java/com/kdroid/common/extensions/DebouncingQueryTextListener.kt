package com.kdroid.common.extensions

import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.coroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Implementing Search-on-type in Android with Coroutines
 */
class DebouncingQueryTextListener(
    lifecycle: Lifecycle,
    private val debouncePeriod:Long = 300L,
    private val onDebouncingQueryTextChange: (String?) -> Unit,
) : SearchView.OnQueryTextListener, LifecycleObserver {

    private val coroutineScope = lifecycle.coroutineScope
    private var searchJob: Job? = null

    override fun onQueryTextSubmit(query: String?): Boolean {
        onDebouncingQueryTextChange(query)
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        searchJob?.cancel()
        searchJob = coroutineScope.launch {
            newText?.let {
                delay(debouncePeriod)
                onDebouncingQueryTextChange(newText)
            }
        }
        return false
    }
}