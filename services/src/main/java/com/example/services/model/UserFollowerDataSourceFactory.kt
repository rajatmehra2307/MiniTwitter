package com.example.services.model

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import com.example.network.models.Tweet
import com.example.network.models.UserInfo

class UserFollowerDataSourceFactory (var dataSource: UserFollowersFriendsDataSource): DataSource.Factory<String, UserInfo>() {
    var userfollowerDataSourceLiveData = MutableLiveData<UserFollowersFriendsDataSource>()
    override fun create(): DataSource<String,UserInfo> {
        userfollowerDataSourceLiveData.postValue(dataSource)
        return dataSource
    }

}