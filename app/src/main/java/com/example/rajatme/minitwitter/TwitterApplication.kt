package com.example.rajatme.minitwitter

import android.app.Application
import com.example.rajatme.minitwitter.di.components.ApplicationComponent
import com.example.rajatme.minitwitter.di.components.DaggerApplicationComponent
import com.example.rajatme.minitwitter.di.modules.ContextModule

class TwitterApplication : Application() {
    var component : ApplicationComponent ?= null
    override fun onCreate() {
        super.onCreate()
        component = DaggerApplicationComponent.builder()
            .contextModule(ContextModule(applicationContext))
            .build()
    }
}