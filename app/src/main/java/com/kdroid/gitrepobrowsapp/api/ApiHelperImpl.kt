package com.kdroid.gitrepobrowsapp.api

import com.kdroid.gitrepobrowsapp.data.Error
import com.kdroid.gitrepobrowsapp.data.IssuesModel
import com.kdroid.gitrepobrowsapp.data.LicenseDTO
import com.kdroid.gitrepobrowsapp.network.NetworkResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ApiHelperImpl(private val apiService: GitService) : ApiHelper {

    override fun getLicenseDetails(url: String): Flow<NetworkResponse<LicenseDTO, Error>> =
        flow { emit(apiService.getLicenseDetails(url)) }

    override fun getIssues(url: String): Flow<NetworkResponse<List<IssuesModel>, Error>> =
        flow { emit(apiService.getIssues(url)) }


}