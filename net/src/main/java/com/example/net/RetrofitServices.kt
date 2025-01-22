package com.example.net

import com.example.net.data.Content
import com.example.net.data.RepositoriesResponse
import com.example.net.data.UsersResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface RetrofitServices {
        @GET("search/users")
        suspend fun getUsers(@Query(value = "q", encoded = true) login: String): UsersResponse

        @GET("search/repositories")
        suspend fun getRepositories(@Query(value = "q", encoded = true) name: String): RepositoriesResponse

        @GET()
        suspend fun getContent(@Url url : String): ArrayList<Content>
}