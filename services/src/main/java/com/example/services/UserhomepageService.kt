package com.example.services
import android.arch.lifecycle.LiveData
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.example.network.TwitterapiService
import com.example.network.models.Tweet
import com.example.network.models.UserInfo
import com.example.services.model.UserhomeDataSource
import com.example.services.model.UserhomeDataSourceFactory
import io.reactivex.Observable


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

}