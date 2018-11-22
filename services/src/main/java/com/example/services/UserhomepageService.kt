package com.example.services
import android.arch.lifecycle.LiveData
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import android.util.Log
import com.example.network.TwitterapiService
import com.example.network.models.Tweet
import com.example.network.models.UserInfo
import com.example.services.model.*
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class UserhomepageService {
    var twitterapiService = TwitterapiService.create()

    fun fetchUserInfo(userName : String) : Observable<UserInfo> {
        var parameterValues = mutableMapOf<String,String> ()
        var result : UserInfo ?= null
        parameterValues.put("screen_name",userName)
        var authorizationHeader = CreateHeaderService.createHeader(parameterValues,"GET","1.1/users/show.json")
        return twitterapiService.getHomepageOfUser(authorizationHeader,userName)

    }

    fun fetchTweetsbyUser(userName : String) : LiveData<PagedList<Tweet>> {
        var dataSourceFactory = UserhomeDataSourceFactory(UserhomeDataSource(userName))
        var result = LivePagedListBuilder(dataSourceFactory,20).build()
        return result
    }

    fun fetchFollowersOfUser(userName : String) : LiveData<PagedList<UserInfo>> {
        var dataSourceFactory = UserFollowerDataSourceFactory(UserFollowersFriendsDataSource(userName,"1.1/followers/list.json"))
        var result = LivePagedListBuilder(dataSourceFactory,20).build()
        return result
    }


    fun followAUser(userName : String) {
        var disposable : Disposable ?= null
        var parameterValues = mutableMapOf<String,String> ()
        parameterValues.put("screen_name",userName)
        parameterValues.put("follow","true")
        var authorizationHeader = CreateHeaderService.createHeader(parameterValues,"POST","1.1/friendships/create.json")
        disposable = twitterapiService.followUser(authorizationHeader,userName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({ result ->
                Log.d("Follow User ->",result.toString())
            },{
                    error -> Log.e("Follow User -> ", error.message)
            })

    }

    fun fetchFriendsOfUser(userName: String) : LiveData<PagedList<UserInfo>>{
        var dataSourceFactory = UserFollowerDataSourceFactory(UserFollowersFriendsDataSource(userName,"1.1/friends/list.json"))
        var result = LivePagedListBuilder(dataSourceFactory,20).build()
        return result
    }

}