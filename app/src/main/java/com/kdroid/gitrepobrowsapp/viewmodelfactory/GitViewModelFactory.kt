package com.kdroid.gitrepobrowsapp.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kdroid.gitrepobrowsapp.ui.githubrepo.github_repo.GithubRepoViewModel
import com.kdroid.gitrepobrowsapp.ui.repository.GitRepository
import com.kdroid.gitrepobrowsapp.ui.repodetails.RepoDetailViewModel

class GitViewModelFactory constructor(private val repository: GitRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(GithubRepoViewModel::class.java)) {
            GithubRepoViewModel(this.repository) as T
        } else if (modelClass.isAssignableFrom(RepoDetailViewModel::class.java)) {
            RepoDetailViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}