package com.example.services.model

import android.arch.paging.PagedList
import android.util.Log
import com.example.database.TimelineCache
import com.example.database.UserTimelineEntity
import com.example.network.TwitterapiService
import com.example.network.getTimeline
import com.example.services.CreateHeaderService
import com.example.services.Utils.BASE_URL

class TimelineBoundaryCallback(private val cache : TimelineCache, var apiservice: TwitterapiService) : PagedList.BoundaryCallback<UserTimelineEntity>() {
    private var isRequestInProgress = false
    private val NETWORK_CALL_SIZE = "50"

    override fun onZeroItemsLoaded() {
        requestAndSave(null,null)
        Log.d("Boundary Callback -> ","Zero Items called")
    }

    override fun onItemAtFrontLoaded(itemAtFront: UserTimelineEntity) {
        var sinceId = itemAtFront.id
        requestAndSave(null,sinceId)
        Log.d("Boundary Callback ->","First Item called ")
    }

    override fun onItemAtEndLoaded(itemAtEnd: UserTimelineEntity) {
        var lastId = itemAtEnd.id
        requestAndSave(lastId,null)
        Log.d("Boundary Callback ->","Last Item called with id ${lastId}")
    }


    fun requestAndSave(maxId : String?, sinceId : String ?) {
        var baseUrl = BASE_URL
        var parameterValues = mutableMapOf<String,String> ()
        parameterValues.put("include_my_retweet","true")
        parameterValues.put("count",NETWORK_CALL_SIZE)
        parameterValues.put("include_entities","true")
        parameterValues.put("tweet_mode","extended")
        if(maxId != null)
            parameterValues.put("max_id",maxId)
        if(sinceId != null)
            parameterValues.put("since_id",sinceId)
        var authorizationHeader = CreateHeaderService.createHeader(parameterValues,"GET","1.1/statuses/home_timeline.json")
        if(isRequestInProgress)
            return
        isRequestInProgress = true
        val tweet_list = getTimeline(apiservice,authorizationHeader,maxId,sinceId) {
            cache.insert(it) {
                isRequestInProgress = false
            }
        }
        isRequestInProgress = false

    }
}