package com.kdroid.gitrepobrowsapp.ui.githubrepo.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kdroid.gitrepobrowsapp.api.ApiService
import com.kdroid.gitrepobrowsapp.data.RepositoryDTO
import timber.log.Timber
import javax.inject.Inject

class ProductPagingSource @Inject constructor(private val apiService: ApiService) :
    PagingSource<Int, RepositoryDTO>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RepositoryDTO> {
        return try {
            val position = params.key ?: 1
            val response = apiService.getAllRepositories(position)
            LoadResult.Page(
                data = response.body()!!.items,
                prevKey = if (position == 1) null else position - 1,
                nextKey = position + 1
            )
        } catch (e: Exception) {
            Timber.d("error in data ${e.message}")
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, RepositoryDTO>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

}