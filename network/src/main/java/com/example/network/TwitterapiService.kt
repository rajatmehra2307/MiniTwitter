package com.example.network

import android.util.Log
import com.example.database.UserTimelineEntity
import com.example.network.models.Tweet
import io.reactivex.Observable
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.Callback
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*



fun getTimeline(api : TwitterapiService,
                authorizationHeader: String,
                maxId : String?,
                sinceId : String?,
                onSuccess: (timeline : MutableList<UserTimelineEntity>?) -> Unit) {
    api.getUserTimeLine(authorizationHeader,maxId = maxId, sinceId = sinceId).enqueue(
        object : Callback<List<Tweet>> {
            override fun onFailure(call: Call<List<Tweet>>, t: Throwable) {
                Log.d("TwitterApiService ->","Failed to load data from Twitter API")
            }

            override fun onResponse(call: Call<List<Tweet>>, response: Response<List<Tweet>>) {
                if(response.isSuccessful){
                    var timeline = mutableListOf<UserTimelineEntity>()
                    var tweets = response.body()
                    for(tweet in tweets!!) {
                        var id = tweet!!.id_str
                        var updateText : String ?= null
                        if(tweet.retweeted_status != null)
                            updateText = tweet!!.retweeted_status!!.full_text
                        else
                            updateText = tweet!!.full_text
                        var timeCreatedAt = tweet.created_at
                        var timeCreatedFormatted = SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy")
                        var date = timeCreatedFormatted.parse(timeCreatedAt)
                        var epoch = date.time

                        timeline.add(UserTimelineEntity(id, updateText,tweet.user.screen_name,epoch,tweet.user.profile_image_url))
                    }
                    onSuccess(timeline)
                }
                else {
                    Log.d("TwitterApiService -> ","Unknown Error")
                }
            }

        }
    )
}


interface TwitterapiService {
    @GET("1.1/statuses/home_timeline.json")
    fun getUserTimeLine(
        @Header("Authorization") authorizationHeader: String,
        @Query("include_my_retweet") shouldIncludeRetweet: Boolean = true,
        @Query("count") totalNumberOfTweets: Long = 50,
        @Query("include_entities") shouldIncludeEntities: Boolean = true,
        @Query("max_id") maxId: String?,
        @Query("since_id")sinceId: String?,
        @Query("tweet_mode") tweetMode: String = "extended"
    ): Call<List<Tweet>>

    companion object Factory {
        fun create(): TwitterapiService {
            var httpClientBuilder = OkHttpClient.Builder()
            var httpClient = httpClientBuilder.build()
            val retrofit = Retrofit.Builder()
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.twitter.com/")
                .build()

            return retrofit.create(TwitterapiService::class.java)
        }
    }
}
