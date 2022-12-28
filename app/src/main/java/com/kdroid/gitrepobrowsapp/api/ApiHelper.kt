package com.kdroid.gitrepobrowsapp.api

import com.kdroid.gitrepobrowsapp.data.Error
import com.kdroid.gitrepobrowsapp.data.IssuesModel
import com.kdroid.gitrepobrowsapp.data.LicenseDTO
import com.kdroid.gitrepobrowsapp.data.RepoDetails
import com.kdroid.gitrepobrowsapp.network.NetworkResponse
import kotlinx.coroutines.flow.Flow

interface ApiHelper {

    fun getLicenseDetails(url:String): Flow<NetworkResponse<LicenseDTO, Error>>

    fun getIssues(url:String): Flow<NetworkResponse<List<IssuesModel>, Error>>
}