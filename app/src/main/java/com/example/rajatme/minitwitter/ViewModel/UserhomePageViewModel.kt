package com.example.rajatme.minitwitter.ViewModel

import android.arch.lifecycle.LiveData
import android.arch.paging.PagedList
import android.os.UserHandle
import com.example.network.models.LookUpResult
import com.example.network.models.Tweet
import com.example.network.models.UserInfo
import com.example.services.UserhomepageService
import io.reactivex.Observable

import javax.inject.Inject

class UserhomePageViewModel @Inject constructor(var userhomepageService: UserhomepageService){

    lateinit var userName : String
    lateinit var userHandle: String
    lateinit var userDescription : String
    lateinit var profilePicUrl : String

    fun followAUser(userName : String) {
        return userhomepageService.followAUser(userName)
    }

    fun fetchUserInfo(screen_id: String) : Observable<UserInfo> {
        return userhomepageService.fetchUserInfo(screen_id)
    }

    fun fetchTweetsbyUser(screen_id : String) :  LiveData<PagedList<Tweet>> {
        return userhomepageService.fetchTweetsbyUser(screen_id)
    }

}