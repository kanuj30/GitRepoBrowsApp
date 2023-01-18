package com.kdroid.gitrepobrowsapp.ui.githubrepo.github_repo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.kdroid.gitrepobrowsapp.data.RepositoryDTO
import com.kdroid.gitrepobrowsapp.ui.repository.GitRepository
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import javax.inject.Inject

class GithubRepoViewModel @Inject constructor(private val repo: GitRepository) : ViewModel() {

    fun fetchRepoList(): Flow<PagingData<RepositoryDTO>> {
        return repo.getAllRepo().cachedIn(viewModelScope)
    }


}