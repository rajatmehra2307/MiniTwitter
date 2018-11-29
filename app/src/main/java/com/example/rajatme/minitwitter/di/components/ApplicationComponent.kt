package com.example.rajatme.minitwitter.di.components


import android.app.Activity
import com.example.rajatme.minitwitter.activities.FollowActivity
import com.example.rajatme.minitwitter.activities.SearchTweetFragment
import com.example.rajatme.minitwitter.activities.TimeLineFragment
import com.example.rajatme.minitwitter.activities.UserhomepageActivity
import com.example.rajatme.minitwitter.di.modules.DatabaseModule

import com.example.rajatme.minitwitter.di.modules.RetrofitModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(RetrofitModule::class,DatabaseModule::class))
interface ApplicationComponent {
    fun inject(activity : UserhomepageActivity)
    fun inject(activity: FollowActivity)
    fun inject(fragment : SearchTweetFragment)
    fun inject(fragment: TimeLineFragment)
}