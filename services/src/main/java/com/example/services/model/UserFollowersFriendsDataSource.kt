package com.example.services.model

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.ItemKeyedDataSource
import android.arch.paging.PageKeyedDataSource
import android.util.Log
import com.example.network.TwitterapiService
import com.example.network.models.UserInfo
import com.example.services.CreateHeaderService
import com.example.services.Utils.NetworkState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class UserFollowersFriendsDataSource(var screenName : String, var url_extension : String, var twitterapiService: TwitterapiService) : PageKeyedDataSource<String, UserInfo>() {


    private var disposable : Disposable ?= null
    var networkState = MutableLiveData<NetworkState>()
    var initialLoading = MutableLiveData<NetworkState>()


    override fun loadInitial(params: LoadInitialParams<String>, callback: LoadInitialCallback<String, UserInfo>) {
        initialLoading.postValue(NetworkState.LOADING)
        networkState.postValue(NetworkState.LOADING)

        var parameterValues = mutableMapOf<String,String> ()
        parameterValues.put("screen_name",screenName)
        var authorizationHeader = CreateHeaderService.createHeader(parameterValues,"GET",url_extension)
        if(url_extension.contains("followers")) {
            disposable = twitterapiService.getFollowersOfUser(authorizationHeader, screenName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    callback.onResult(result.userList, null, result.nextCursor)
                    initialLoading.postValue(NetworkState.LOADED)
                    networkState.postValue(NetworkState.LOADED)

                }, { error ->
                    Log.e("Datasource for follower", error.message)
                    initialLoading.postValue(NetworkState(NetworkState.Status.FAILED,error.message!!))
                    networkState.postValue(NetworkState(NetworkState.Status.FAILED,error.message!!))
                })
        }
        else if(url_extension.contains("friends")) {
            disposable = twitterapiService.getFriendsOfUser(authorizationHeader, screenName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    callback.onResult(result.userList, null, result.nextCursor)
                    initialLoading.postValue(NetworkState.LOADED)
                    networkState.postValue(NetworkState.LOADED)
                }, { error ->
                    Log.e("Datasource for follower", error.message)
                    initialLoading.postValue(NetworkState(NetworkState.Status.FAILED,error.message!!))
                    networkState.postValue(NetworkState(NetworkState.Status.FAILED,error.message!!))
                })
        }
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, UserInfo>) {
//        networkState.postValue(NetworkState.LOADING)
        var parameterValues = mutableMapOf<String,String> ()
        parameterValues.put("screen_name",screenName)
        parameterValues.put("cursor", params.key)
        var authorizationHeader = CreateHeaderService.createHeader(parameterValues,"GET",url_extension)
        if(url_extension.contains("followers")) {
            disposable = twitterapiService.getFollowersOfUser(authorizationHeader, screenName, params.key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    callback.onResult(result.userList, result.nextCursor)
//                    networkState.postValue(NetworkState.LOADED)
                }, { error ->
                    Log.e("Datasource for follower", error.message)
//                    networkState.postValue(NetworkState(NetworkState.Status.FAILED,error.message!!))
                })
        }
        else if(url_extension.contains("friends")) {
            disposable = twitterapiService.getFriendsOfUser(authorizationHeader, screenName, params.key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    callback.onResult(result.userList, result.nextCursor)
//                    networkState.postValue(NetworkState.LOADED)
                }, { error ->
                    Log.e("Datasource for follower", error.message)
//                    networkState.postValue(NetworkState(NetworkState.Status.FAILED,error.message!!))
                })
        }
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<String, UserInfo>) {

    }
}