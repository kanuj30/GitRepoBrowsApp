package com.kdroid.gitrepobrowsapp.ui.githubrepo.github_repo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.kdroid.gitrepobrowsapp.data.RepositoryDTO
import com.kdroid.gitrepobrowsapp.ui.repository.GitRepository
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.flow.Flow

class GithubRepoViewModel @AssistedInject constructor(
    private val repo: GitRepository,
) : ViewModel() {

    companion object {
        private const val DEFAULT_QUERY = "kotlin"
        private const val CURRENT_QUERY = "current_query"

    }

    //private val currentQuery = state.getLiveData(CURRENT_QUERY, DEFAULT_QUERY)
    private val currentQuery = MutableLiveData(DEFAULT_QUERY)

    fun fetchRepoList(): Flow<PagingData<RepositoryDTO>> {
        return repo.getAllRepo()
    }

    val searchData = currentQuery.switchMap { queryString ->
        repo.getSearchRepo(queryString).cachedIn(viewModelScope)
    }

    fun searchQueryChanged(searchQuery: String = "") {
        currentQuery.value = searchQuery
    }

    fun resetSearch() {
        currentQuery.value = ""
    }

}