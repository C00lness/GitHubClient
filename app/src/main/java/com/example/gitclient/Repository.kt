package com.example.gitclient

import com.example.gitclient.data.CombinedData
import com.example.gitclient.data.GitRepositories
import com.example.gitclient.net.RetrofitServices
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow

class Repository(private val service: RetrofitServices) {
    fun getUsers (login: String) = flow{emit(service.getUsers(login))}
    fun getRepositories (name: String) = flow{emit(service.getRepositories(name))}

    fun getCombinedData(): Flow<CombinedData> {
        return combine(getUsers("ABCD+in:login"), getRepositories("ABCD+in:name")) { users, repositories ->
            CombinedData(users = users, repositories = repositories)
        }
    }
}

