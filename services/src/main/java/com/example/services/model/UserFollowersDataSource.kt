package com.example.services.model

import android.arch.paging.ItemKeyedDataSource
import android.arch.paging.PageKeyedDataSource
import android.util.Log
import com.example.network.TwitterapiService
import com.example.network.models.UserInfo
import com.example.services.CreateHeaderService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class UserFollowersFriendsDataSource(var screenName : String, var url_extension : String) : PageKeyedDataSource<String, UserInfo>() {


    private var disposable : Disposable ?= null
    private var twitterapiService = TwitterapiService.create()

    override fun loadInitial(params: LoadInitialParams<String>, callback: LoadInitialCallback<String, UserInfo>) {
        var parameterValues = mutableMapOf<String,String> ()
        parameterValues.put("screen_name",screenName)
        var authorizationHeader = CreateHeaderService.createHeader(parameterValues,"GET",url_extension)
        if(url_extension.contains("followers")) {
            disposable = twitterapiService.getFollowersOfUser(authorizationHeader, screenName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    callback.onResult(result.userList, null, result.nextCursor)
                }, { error ->
                    Log.e("Datasource for follower", error.message)
                })
        }
        else if(url_extension.contains("friends")) {
            disposable = twitterapiService.getFriendsOfUser(authorizationHeader, screenName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    callback.onResult(result.userList, null, result.nextCursor)
                }, { error ->
                    Log.e("Datasource for follower", error.message)
                })
        }
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, UserInfo>) {
        var parameterValues = mutableMapOf<String,String> ()
        parameterValues.put("screen_name",screenName)
        parameterValues.put("cursor", params.key)
        var authorizationHeader = CreateHeaderService.createHeader(parameterValues,"GET",url_extension)
        if(url_extension.contains("followers")) {
            disposable = twitterapiService.getFollowersOfUser(authorizationHeader, screenName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    callback.onResult(result.userList, result.nextCursor)
                }, { error ->
                    Log.e("Datasource for follower", error.message)
                })
        }
        else if(url_extension.contains("friends")) {
            disposable = twitterapiService.getFriendsOfUser(authorizationHeader, screenName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    callback.onResult(result.userList, result.nextCursor)
                }, { error ->
                    Log.e("Datasource for follower", error.message)
                })
        }
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<String, UserInfo>) {

    }
}