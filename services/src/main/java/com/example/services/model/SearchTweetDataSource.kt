package com.example.services.model

import android.arch.paging.ItemKeyedDataSource
import android.util.Log
import com.example.network.TwitterapiService
import com.example.network.models.Tweet
import com.example.services.CreateHeaderService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class SearchTweetDataSource (var query : String, var api : TwitterapiService) : ItemKeyedDataSource<String, Tweet>() {
    private var disposable : Disposable ?= null
    override fun loadInitial(params: LoadInitialParams<String>, callback: LoadInitialCallback<Tweet>) {
        var parameterValues = mutableMapOf<String,String> ()
        parameterValues.put("q",query)
        parameterValues.put("tweet_mode","extended")
        parameterValues.put("include_my_retweet","true")
        var authorizationHeader = CreateHeaderService.createHeader(parameterValues,"GET","1.1/search/tweets.json")
        disposable = api.searchTweets(authorizationHeader,query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({ result ->
                callback.onResult(result.tweetList)
            },{
                    error -> Log.e("SearchTweetDataSource->", error.message)
            })
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<Tweet>) {
        var maxId = ((params.key).toLong() - 1).toString()
        var parameterValues = mutableMapOf<String,String> ()
        parameterValues.put("q",query)
        parameterValues.put("tweet_mode","extended")
        parameterValues.put("include_my_retweet","true")
        parameterValues.put("max_id",maxId)
        var authorizationHeader = CreateHeaderService.createHeader(parameterValues,"GET","1.1/search/tweets.json")
        disposable = api.searchTweets(authorizationHeader,query,maxId = maxId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({ result ->
                callback.onResult(result.tweetList)
            },{
                    error -> Log.e("SearchTweetDataSource->", error.message)
            })

    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<Tweet>) {

    }

    override fun getKey(item: Tweet): String {
        return item.id
    }


}