package com.kdroid.gitrepobrowsapp.ui.repodetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kdroid.common.ViewState
import com.kdroid.gitrepobrowsapp.data.IssuesModel
import com.kdroid.gitrepobrowsapp.data.LicenseDTO
import com.kdroid.gitrepobrowsapp.data.RepoDetails
import com.kdroid.gitrepobrowsapp.network.NetworkResponse
import com.kdroid.gitrepobrowsapp.ui.repository.GitRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class RepoDetailViewModel @Inject constructor(val gitRepository: GitRepository) : ViewModel() {

    val TAG = RepoDetailViewModel::class.java.name
    private val _repoData = MutableStateFlow<ViewState<RepoDetails>>(ViewState.loading())
    val onRepoData: StateFlow<ViewState<RepoDetails>> = _repoData

    fun getRepoDetailData(licenceUrl: String?, issueURL: String?) {
        Timber.d("getRepoDetailData() called with: licenceUrl = $licenceUrl, issueURL = $issueURL")
        if (!licenceUrl.isNullOrEmpty() && !issueURL.isNullOrEmpty()) {
            viewModelScope.launch(Dispatchers.Main) {
                gitRepository.getLicenseDetails(licenceUrl)
                    .zip(gitRepository.getIssues(issueURL)) { licenceData, issueData ->
                        var licenceInfo: LicenseDTO? = null
                        var issueInfo: List<IssuesModel>? = null

                        when (licenceData) {
                            is NetworkResponse.Success -> licenceInfo = licenceData.body
                            else -> {}
                        }
                        when (issueData) {
                            is NetworkResponse.Success -> issueInfo = issueData.body
                            else -> {}
                        }
                        return@zip (ViewState.success(RepoDetails(licenceInfo!!, issueInfo!!)))
                    }.flowOn(Dispatchers.IO).catch { error ->
                        Timber.d("api error $error")
                        _repoData.value = ViewState.Error("Error")
                    }.collect {
                        _repoData.value = it
                    }
            }
        }
    }
}