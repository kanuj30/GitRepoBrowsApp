package com.kdroid.gitrepobrowsapp.api

import com.kdroid.gitrepobrowsapp.data.*
import com.kdroid.gitrepobrowsapp.network.NetworkResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiService {
    /**
     *
     */
    //@GET("search/repositories?q=stars:>=1000&sort=stars")
    //q=stars:>=1000+language:kotlin
    @GET("search/repositories")
    suspend fun getAllRepositories(
        @Query("q") query: String = "retrofit",
        @Query("language") language: String = "kotlin",
        @Query("stars") stars: String = ">=1000",
        @Query("page") page: Int = 1,
    ): retrofit2.Response<Response>


    @GET("search/repositories")
    suspend fun searchRepositories(
        @Query("q") query: String,
        @Query("language") language: String = "kotlin",
        @Query("page") page: Int = 1,
    ): retrofit2.Response<Response>

    @GET
    suspend fun getLicenseDetails(@Url url: String): NetworkResponse<LicenseDTO, Error>

    @GET
    suspend fun getIssues(@Url url: String): NetworkResponse<List<IssuesModel>, Error>

}