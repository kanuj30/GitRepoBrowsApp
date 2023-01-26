package com.kdroid.gitrepobrowsapp.ui.githubrepo.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kdroid.gitrepobrowsapp.api.ApiService
import com.kdroid.gitrepobrowsapp.data.RepositoryDTO
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

private const val GITHUB_STARTING_PAGE_INDEX = 1

class GitRepoSearchPagingSource @Inject constructor(
    private val apiService: ApiService,
    private var query: String = "",
) : PagingSource<Int, RepositoryDTO>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RepositoryDTO> {
        return try {
            val position = params.key ?: GITHUB_STARTING_PAGE_INDEX

            val response = if (query.isEmpty()) apiService.getAllRepositories(page = position)
            else apiService.getAllRepositories(
                query = query, page = position
            )

            LoadResult.Page(
                data = response.body()!!.items,
                prevKey = if (position == 1) null else position - 1,
                nextKey = position + 1
            )
        } catch (exception: IOException) {
            Timber.d("error in data ${exception.message}")
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
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