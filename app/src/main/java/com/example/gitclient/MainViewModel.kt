package com.example.gitclient

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gitclient.data.CombinedData
import com.example.gitclient.data.GitRepositories
import com.example.gitclient.data.GitUsers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository): ViewModel() {

    val users = MutableStateFlow(GitUsers())
    val repositories = MutableStateFlow(GitRepositories())

    fun getUsers(login: String) = viewModelScope.launch {
        repository.getUsers(login)
            .flowOn(Dispatchers.IO)
            .catch {  }
            .collect{ value ->
                Log.d("ResultFlow", value.toString())
                users.value = value
            }
    }

    fun getRepositories(name: String) = viewModelScope.launch {
        repository.getRepositories(name)
            .flowOn(Dispatchers.IO)
            .catch {  }
            .collect{ value ->
                Log.d("ResultFlow", value.toString())
                repositories.value = value
            }
    }

    fun getCombinedData() = viewModelScope.launch {
        repository.getCombinedData()
            .flowOn(Dispatchers.IO)
            .catch {  }
            .collect{ value ->
                Log.d("ResultFlow", value.users.toString())
                Log.d("ResultFlow", value.repositories.toString())
                //repositories.value = value
            }
    }
}