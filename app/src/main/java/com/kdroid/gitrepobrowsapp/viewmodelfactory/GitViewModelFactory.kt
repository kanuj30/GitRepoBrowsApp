package com.kdroid.gitrepobrowsapp.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import javax.inject.Inject
import javax.inject.Provider

class GitViewModelFactory @Inject constructor(
    private val viewModelMap: Map<Class<out ViewModel>, Provider<ViewModel>>,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        if (viewModelMap.containsKey(modelClass)) {
            return viewModelMap[modelClass]?.get() as T
        }
        throw IllegalArgumentException("invalid view-model key supplied")
    }
}