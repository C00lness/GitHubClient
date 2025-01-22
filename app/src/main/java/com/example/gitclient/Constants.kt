package com.example.gitclient

import com.example.gitclient.net.RetrofitServices
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

object Constants {
    const val BASE_URL = "https://api.github.com/search/"
    //https://api.github.com/search/repositories?q=abcd+in:name
    //https://api.github.com/search/users?q=octocat+in:login
    const val NETWORK_TIMEOUT = 60L
}

fun provideRetrofit(baseUrl: String, gson: Gson, client: OkHttpClient): RetrofitServices =
    Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(RetrofitServices::class.java)

fun provideOkHttpClient() = if(BuildConfig.DEBUG){
    val loggingInterceptor = HttpLoggingInterceptor()
    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS)
    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

    val requestInterceptor = Interceptor{chain ->
        val url = chain.request()
            .url
            .newBuilder()
            .build()

        val request = chain.request()
            .newBuilder()
            .url(url)
            .build()

        return@Interceptor chain.proceed(request)
    }

    OkHttpClient
        .Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(requestInterceptor)
        .build()
}
else
{
    OkHttpClient
        .Builder()
        .build()
}

fun provideJson(): Gson = GsonBuilder().setLenient().create()
fun providesBaseUrl() = "https://api.github.com/search/"

fun getUnsafeOkHttpClient(): OkHttpClient.Builder {
    return try {
        // Create a trust manager that does not validate certificate chains
        val trustAllCerts = arrayOf<TrustManager>(
            object : X509TrustManager {
                @Throws(CertificateException::class)
                override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
                }

                @Throws(CertificateException::class)
                override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
                }

                override fun getAcceptedIssuers(): Array<X509Certificate> {
                    return arrayOf()
                }
            }
        )

        // Install the all-trusting trust manager
        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCerts, SecureRandom())

        // Create an ssl socket factory with our all-trusting manager
        val sslSocketFactory: SSLSocketFactory = sslContext.socketFactory
        val builder = OkHttpClient.Builder()
        builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
        builder.hostnameVerifier { hostname, session -> true }
        builder
    } catch (e: Exception) {
        throw RuntimeException(e)
    }
}

data class DataStatus<out T>(
    val status: Status, val data: T? = null, val msg: String? = null)
{
    enum class Status{
        LOADING, SUCCESS, ERROR
    }

    fun <T> loading(): DataStatus<T>{
        return DataStatus(Status.LOADING)
    }

    fun<T> success(data: T?): DataStatus<T>{
        return DataStatus(Status.SUCCESS, data)
    }

    fun<T> error(error: String): DataStatus<T>{
        return DataStatus(Status.ERROR, msg = error)
    }
}