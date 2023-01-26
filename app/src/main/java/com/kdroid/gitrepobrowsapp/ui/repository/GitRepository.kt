package com.kdroid.gitrepobrowsapp.ui.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.kdroid.gitrepobrowsapp.api.ApiConstant
import com.kdroid.gitrepobrowsapp.api.ApiHelper
import com.kdroid.gitrepobrowsapp.api.ApiService
import com.kdroid.gitrepobrowsapp.data.Error
import com.kdroid.gitrepobrowsapp.data.IssuesModel
import com.kdroid.gitrepobrowsapp.data.LicenseDTO
import com.kdroid.gitrepobrowsapp.data.RepositoryDTO
import com.kdroid.gitrepobrowsapp.network.NetworkResponse
import com.kdroid.gitrepobrowsapp.ui.githubrepo.paging.GitRepoSearchPagingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GitRepository @Inject constructor(
    private val dataSource: GitRepoSearchPagingSource,
    private val apiService: ApiService,
) : ApiHelper {


    fun getAllRepo(): Flow<PagingData<RepositoryDTO>> {
        return Pager(
            config = PagingConfig(
                pageSize = ApiConstant.PAGE_SIZE, enablePlaceholders = false, initialLoadSize = 2
            ), pagingSourceFactory = {
                dataSource
            }, initialKey = 1
        ).flow
    }

    fun getSearchRepo(query: String): LiveData<PagingData<RepositoryDTO>> {
        return Pager(
            config = PagingConfig(
                pageSize = ApiConstant.PAGE_SIZE, enablePlaceholders = false, initialLoadSize = 2
            ), pagingSourceFactory = {
                GitRepoSearchPagingSource(apiService,query)
            }, initialKey = 1
        ).liveData
    }

    override fun getLicenseDetails(url: String): Flow<NetworkResponse<LicenseDTO, Error>> =
        flow { emit(apiService.getLicenseDetails(url)) }

    override fun getIssues(url: String): Flow<NetworkResponse<List<IssuesModel>, Error>> =
        flow { emit(apiService.getIssues(url)) }


}