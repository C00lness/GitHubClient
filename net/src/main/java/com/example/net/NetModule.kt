package com.example.net

import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://api.github.com/"

fun getRetrofitInstance(base_Url: String): Retrofit {
    return Retrofit.Builder()
        .baseUrl(base_Url)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

fun getRetrofitInterface(retrofit: Retrofit): RetrofitServices {
    return retrofit.create(RetrofitServices::class.java)
}

val netModule = module {
    single { BASE_URL }
    single { getRetrofitInstance(get()) }
    single { getRetrofitInterface(get()) }
}

