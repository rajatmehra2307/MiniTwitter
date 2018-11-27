package com.example.rajatme.minitwitter.di.modules

import com.example.network.TwitterapiService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class RetrofitModule {
    @Provides
    @Singleton
    fun getApiInterface(retrofit : Retrofit) : TwitterapiService{
        return retrofit.create(TwitterapiService::class.java)
    }

    @Provides
    fun getRetrofit(okHttpClient: OkHttpClient) : Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl("https://api.twitter.com/")
            .build()

    }

    @Provides
    fun getOkHttpClient() : OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(60,TimeUnit.SECONDS)
            .build()

    }
}