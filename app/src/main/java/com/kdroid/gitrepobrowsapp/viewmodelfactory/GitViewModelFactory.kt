package com.kdroid.gitrepobrowsapp.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kdroid.gitrepobrowsapp.ui.githubrepo.main.MainViewModel
import com.kdroid.gitrepobrowsapp.ui.repo.GitRepository
import com.kdroid.gitrepobrowsapp.ui.repodetails.RepoDetailViewModel

class GitViewModelFactory constructor(private val repository: GitRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            MainViewModel(this.repository) as T
        } else if (modelClass.isAssignableFrom(RepoDetailViewModel::class.java)) {
            RepoDetailViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}