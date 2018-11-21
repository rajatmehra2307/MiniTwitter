package com.example.services.model

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import com.example.network.models.Tweet

class UserhomeDataSourceFactory(var dataSource: UserhomeDataSource): DataSource.Factory<Long, Tweet>() {
    var userhomeDataSourceLiveData = MutableLiveData<UserhomeDataSource>()
    override fun create(): DataSource<Long, Tweet> {
        userhomeDataSourceLiveData.postValue(dataSource)
        return dataSource
    }

}