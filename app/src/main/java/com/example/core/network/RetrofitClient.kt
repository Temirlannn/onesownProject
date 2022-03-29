package com.example.core.network

import com.example.data.remote.YoutubeAPI
import com.example.youtube.BuildConfig.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    factory { provideInterceptor() }
    factory { provideOkhttpClient(get()) }
    factory { provideRetrofit(get()) }
}

fun provideRetrofit(okHttpClient: OkHttpClient): YoutubeAPI {

    return  Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build().create(YoutubeAPI::class.java)
}

fun provideOkhttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
    return OkHttpClient().newBuilder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(interceptor)
        .build()
}

fun provideInterceptor(): HttpLoggingInterceptor {
    val interceptor = HttpLoggingInterceptor()
    return interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

}
