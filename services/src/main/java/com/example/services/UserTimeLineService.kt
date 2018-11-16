package com.example.services

import com.example.network.TwitterapiService

import com.example.network.models.Tweet
import com.example.services.Utils.BASE_URL
import io.reactivex.disposables.Disposable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.*

class UserTimeLineService() {
    val apiservice by lazy {
        TwitterapiService.create()
    }
    fun fetchUserTimeLine(shouldIncludeRetweet : Boolean, shouldIncludeEntities : Boolean , tweetMode: String ) : Observable<List<Tweet>>? {
        var twitterTimelineResult : Observable<List<Tweet>> ?= null
        var baseUrl = BASE_URL
        var parameterValues = mutableMapOf<String,String> ()
        parameterValues.put("include_my_retweet",shouldIncludeEntities.toString())
        parameterValues.put("include_entities",shouldIncludeEntities.toString())
        parameterValues.put("tweet_mode",tweetMode)
        var authorizationHeader = CreateHeaderService.createHeader(parameterValues,"GET")
        return apiservice.getUserTimeLine(authorizationHeader,shouldIncludeRetweet,shouldIncludeEntities,tweetMode)
    }

    companion object {
        fun create() : UserTimeLineService {
            return UserTimeLineService()
        }
    }
}