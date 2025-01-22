package com.example.gitclient.di
import com.example.gitclient.MainViewModel
import com.example.gitclient.Repository
import com.example.gitclient.net.RetrofitServices
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://api.github.com/search/"

fun getRetrofitInstance(base_Url: String): Retrofit {
    return Retrofit.Builder()
        .baseUrl(base_Url)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

fun getRetrofitInterface(retrofit: Retrofit): RetrofitServices {
    return retrofit.create(RetrofitServices::class.java)
}


val apiModule = module {
    single { BASE_URL }
    single { getRetrofitInstance(get()) }
    single { getRetrofitInterface(get()) }
}

val netModule = module {
    factory { Repository(get())}
    viewModel { MainViewModel(get()) }
}

