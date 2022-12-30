package com.kdroid.gitrepobrowsapp.ui.githubrepo.github_repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kdroid.common.ViewState
import com.kdroid.gitrepobrowsapp.data.Response
import com.kdroid.gitrepobrowsapp.network.NetworkResponse
import com.kdroid.gitrepobrowsapp.ui.repository.GitRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class GithubRepoViewModel @Inject constructor(private val gitRepository: GitRepository) : ViewModel() {

    private val _state = MutableLiveData<ViewState<Response>>()
    val state: LiveData<ViewState<Response>> get() = _state


    fun fetchRepoList() {
        viewModelScope.launch(Dispatchers.Default) {
            _state.postValue(ViewState.loading())
            gitRepository.let {
                when (val item = it.getAllRepo()) {
                    is NetworkResponse.Success -> _state.postValue(ViewState.success(item.body))
                    is NetworkResponse.ApiError -> _state.postValue(ViewState.error("error"))
                    is NetworkResponse.NetworkError -> _state.postValue(ViewState.error("Network Error"))
                    is NetworkResponse.UnknownError -> _state.postValue(ViewState.error("Unknown"))
                }
            }
        }
    }

}