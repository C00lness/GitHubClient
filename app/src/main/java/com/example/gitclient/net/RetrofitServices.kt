package com.example.gitclient.net


import com.example.gitclient.data.GitRepositories
import com.example.gitclient.data.GitUsers
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitServices {
    @GET("users")
    suspend fun getUsers(@Query(value = "q", encoded = true) login: String): GitUsers

    @GET("repositories")
    suspend fun getRepositories(@Query(value = "q", encoded = true) name: String): GitRepositories
    //https://api.github.com/search/repositories?q=abcd+in:name
}