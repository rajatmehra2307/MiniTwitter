package com.example.network

import android.util.Log
import com.example.network.models.Tweet
import io.reactivex.Observable
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query
import java.io.IOException
import java.util.*

interface TwitterapiService {
    @GET("1.1/statuses/home_timeline.json")
    fun getUserTimeLine(@Header("Authorization") authorizationHeader : String,
                        @Query("include_my_retweet") shouldIncludeRetweet: Boolean,
                        @Query("include_entities") shouldIncludeEntities: Boolean,
                        @Query("tweet_mode") tweetMode:String) : Observable<List<Tweet> >
    companion object Factory {
        fun create(): TwitterapiService {
            var httpClientBuilder = OkHttpClient.Builder()
//            val interceptor = Interceptor { chain ->
//                var original = chain.request()
//                var builder = original.newBuilder().addHeader("Authorization", "OAuth oauth_consumer_key=\"Ld33h4CRUjvvTfCiLsfIxgoyd\",oauth_signature_method=\"HMAC-SHA1\",oauth_timestamp=\"1542199613\",oauth_nonce=\"2367427473\",oauth_version=\"1.0\",oauth_token=\"1055800242916880385-tLLohdyFXM4mw9qqdGljpwPJXZAYZp\",oauth_signature=\"VxzymbQKdtx1DKLp0NIN7uzyBas%3D\"")
//                var request = builder.build()
//                chain.proceed(request)
//            }
//            httpClientBuilder.addInterceptor(interceptor)
            var httpClient = httpClientBuilder.build()
            val retrofit = Retrofit.Builder()
                .client(httpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.twitter.com/")
                .build()

            return retrofit.create(TwitterapiService::class.java)
        }
    }

}