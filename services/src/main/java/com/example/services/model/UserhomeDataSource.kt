package com.example.services.model

import android.arch.paging.ItemKeyedDataSource
import android.util.Log
import com.example.database.UserTimelineEntity
import com.example.network.TwitterapiService
import com.example.network.models.Tweet
import com.example.services.CreateHeaderService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class UserhomeDataSource(var screenName : String, var api : TwitterapiService) : ItemKeyedDataSource<Long, Tweet>() {
    private var disposable : Disposable ?= null
    override fun loadInitial(params: LoadInitialParams<Long>, callback: LoadInitialCallback<Tweet>) {
        var parameterValues = mutableMapOf<String,String> ()
        parameterValues.put("screen_name",screenName)
        parameterValues.put("tweet_mode","extended")
        var authorizationHeader = CreateHeaderService.createHeader(parameterValues,"GET","1.1/statuses/user_timeline.json")
        disposable = api.getTweetsByuser(authorizationHeader,screenName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({ result ->
                callback.onResult(result)
            },{
                    error -> Log.e("Creating datasource", error.message)
            })
    }

    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<Tweet>) {
        var parameterValues = mutableMapOf<String,String> ()
        parameterValues.put("screen_name",screenName)
        parameterValues.put("tweet_mode","extended")
        parameterValues.put("max_id",(params.key - 1).toString())
        var authorizationHeader = CreateHeaderService.createHeader(parameterValues,"GET","1.1/statuses/user_timeline.json")
        disposable = api.getTweetsByuser(authorizationHeader,screenName,(params.key - 1).toString())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({ result ->
                callback.onResult(result)
            },{
                    error -> Log.e("Creating datasource", error.message)
            })
    }

    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<Tweet>) {

    }

    override fun getKey(item: Tweet): Long {
        return (item.id).toLong()
    }

}
