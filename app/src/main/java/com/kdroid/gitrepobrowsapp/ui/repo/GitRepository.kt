package com.kdroid.gitrepobrowsapp.ui.repo

import com.kdroid.gitrepobrowsapp.api.ApiHelper
import com.kdroid.gitrepobrowsapp.api.ApiService
import com.kdroid.gitrepobrowsapp.data.Error
import com.kdroid.gitrepobrowsapp.data.IssuesModel
import com.kdroid.gitrepobrowsapp.data.LicenseDTO
import com.kdroid.gitrepobrowsapp.data.Response
import com.kdroid.gitrepobrowsapp.network.NetworkResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GitRepository(private val apiService: ApiService) : ApiHelper {

    suspend fun getAllRepo(): NetworkResponse<Response, Error> = apiService.getAllRepositories()

    override fun getLicenseDetails(url: String): Flow<NetworkResponse<LicenseDTO, Error>> =
        flow { emit(apiService.getLicenseDetails(url)) }

    override fun getIssues(url: String): Flow<NetworkResponse<List<IssuesModel>, Error>> =
        flow { emit(apiService.getIssues(url)) }


}