package com.kdroid.gitrepobrowsapp.ui.repo

import com.kdroid.gitrepobrowsapp.data.Error
import com.kdroid.gitrepobrowsapp.data.IssuesModel
import com.kdroid.gitrepobrowsapp.data.LicenseDTO
import com.kdroid.gitrepobrowsapp.data.Response
import com.kdroid.gitrepobrowsapp.network.ApiClient
import com.kdroid.gitrepobrowsapp.network.NetworkResponse

class GitRepository {

    suspend fun getAllRepo(): NetworkResponse<Response, Error> {
        return ApiClient.request!!.getAllRepositories()
    }

    suspend fun getLicenseDetails(url: String): NetworkResponse<LicenseDTO, Error> {
        return ApiClient.request!!.getLicenseDetails(url)
    }

    suspend fun getIssueDetails(url: String): NetworkResponse<List<IssuesModel>, Error> {
        return ApiClient.request!!.getIssues(url)
    }

}