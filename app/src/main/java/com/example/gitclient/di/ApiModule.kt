package com.example.gitclient.di
import com.example.gitclient.MainViewModel
import com.example.repository.Repository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val apiModule = module {
    factory { Repository(get()) }
    viewModel { MainViewModel(get()) }
}

