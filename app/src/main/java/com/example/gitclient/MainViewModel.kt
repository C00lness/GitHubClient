package com.example.gitclient

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.core.DataStatus
import com.example.net.data.Content
import com.example.repository.CombinedData
import com.example.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository): ViewModel() {

    var url: String by mutableStateOf("")

    private val _content = MutableStateFlow<List<Content>>(listOf())
    val content: StateFlow<List<Content>> get() = _content

    private val _combinedData : MutableStateFlow<DataStatus<CombinedData>?> = MutableStateFlow(null)
    val combinedData : StateFlow<DataStatus<CombinedData>?> get() = _combinedData

    fun getCombinedData(login: String, name: String) = viewModelScope.launch {
        repository.getCombinedData(login, name)
            .flowOn(Dispatchers.IO)
            .catch { e -> _combinedData.emit(DataStatus.Error(e.toString()))}
            .collect{ value ->
                if (value.items.size > 0)
                    _combinedData.emit(DataStatus.Success(CombinedData(items = value.items)))
                else _combinedData.emit(DataStatus.Empty())
            }
    }

    fun getContent(url: String) = viewModelScope.launch {
        repository.getContent(url)
            .flowOn(Dispatchers.IO)
            .catch {}
            .collect{ value ->
                _content.emit(value.sortedWith(compareBy({it.type}, {it.name})))
            }
    }
}