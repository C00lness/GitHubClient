package com.example.repository


import com.example.core.InterfaceType
import com.example.net.RetrofitServices
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow

class Repository(private val service: RetrofitServices) {
    fun getUsers(login: String) = flow { emit(service.getUsers(login).items) }
    fun getRepositories(name: String) = flow { emit(service.getRepositories(name).items) }

    fun getCombinedData(login: String, name: String): Flow<CombinedData> {
        return combine(getUsers(login), getRepositories(name)) { users, repositories ->
            val result = arrayListOf<InterfaceType>()
            result.addAll(users)
            result.addAll(repositories)
            CombinedData(items = result.sortedBy { it.named })
        }
    }

    fun getContent(url: String) = flow { emit(service.getContent(url)) }
}
