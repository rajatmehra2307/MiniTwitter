package com.example.rajatme.minitwitter.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class ContextModule (var context : Context){
    @Provides
    fun provideContext() = context
}