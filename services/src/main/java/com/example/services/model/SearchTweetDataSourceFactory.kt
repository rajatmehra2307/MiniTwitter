package com.example.services.model

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import com.example.network.models.Tweet


class SearchTweetDataSourceFactory (var dataSource: SearchTweetDataSource): DataSource.Factory<String, Tweet>() {
    var searchTweetDataSourceLiveData = MutableLiveData<SearchTweetDataSource>()
    override fun create(): DataSource<String, Tweet> {
        searchTweetDataSourceLiveData.postValue(dataSource)
        return dataSource
    }

}