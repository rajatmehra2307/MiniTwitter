package com.example.rajatme.minitwitter.viewModel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.paging.PagedList
import android.databinding.ObservableField
import android.util.Log
import com.example.network.models.Tweet
import com.example.network.models.UserInfo
import com.example.rajatme.minitwitter.Utils.formatNumber
import com.example.services.UserhomepageService
import com.example.services.Utils.NetworkState

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

import javax.inject.Inject


class UserhomePageViewModel @Inject constructor(var userhomepageService: UserhomepageService){

    var userName =  ObservableField<String>()
    var userHandle =  ObservableField<String>()
    var userDescription = ObservableField<String>()
    var profilePicUrl = ObservableField<String>()
    var followers = ObservableField<String>()
    var following = ObservableField<String>()
    var state = MutableLiveData<NetworkState.Status>().apply { value = NetworkState.Status.RUNNING }
    var buttonStatus = ObservableField<Boolean>(false)


    fun followAUser(userName : String) {
        buttonStatus.set(false)
        return userhomepageService.followAUser(userName)
    }

    fun fetchUserInfo(screen_id: String) {
         userhomepageService.fetchUserInfo(screen_id)
            .subscribeOn(Schedulers.io())
             .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                result ->
                    lookUpConnection(result)

            },{ error ->
                    Log.e("UserhomepageViewModel->",error.message)
                    state.value = NetworkState.Status.FAILED
            })
    }

    fun fetchTweetsbyUser(screen_id : String) :  LiveData<PagedList<Tweet>> {
        return userhomepageService.fetchTweetsbyUser(screen_id)
    }


    fun lookUpConnection(userInfo: UserInfo){
        userhomepageService.lookUpConnection(userInfo.handle)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({
                result -> setUpViewModel(userInfo)
                if(result!= null && !result.isEmpty()) {
                    var temp = result.get(0)
                    for (connection in temp.connection)
                        if (connection.contains("none"))
                            buttonStatus.set(true)
                }
            }, { error ->
                Log.e("UserhomepageViewModel",error.message)
                        state.value = NetworkState.Status.FAILED
            })
    }

    fun setUpViewModel(userInfo: UserInfo) {
        userName.set(userInfo.name)
        userHandle.set(userInfo.handle)
        profilePicUrl.set(userInfo.imageUrl)
        followers.set(formatNumber(userInfo.followers) + " followers")
        following.set(formatNumber(userInfo.friends) + " followers")
        userDescription.set(userInfo.description)
        state.value = NetworkState.Status.SUCCESS

    }

}