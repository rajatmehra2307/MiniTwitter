package com.example.rajatme.minitwitter

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.example.rajatme.minitwitter.Utils.*
import com.twitter.sdk.android.core.identity.TwitterLoginButton
import twitter4j.Twitter
import twitter4j.TwitterException
import twitter4j.TwitterFactory
import twitter4j.auth.RequestToken
import java.lang.Exception

class LoginActivity : AppCompatActivity() {

    private val TAG = "LoginActivity"
    private var twitter: Twitter ?= null
    private var sharedPreference: SharedPreferences ?= null
    private var twitterLoginBtn : Button?= null
    private var requestToken:RequestToken ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreference = getSharedPreferences(PREFERENCE_NAME,0)
        if(!isUserLoggedInAlready()) {
            setContentView(R.layout.activity_login)
            twitterLoginBtn = findViewById(R.id.login_button)
            twitterLoginBtn!!.setOnClickListener{loginToTwitter()}
        }
        else {
            setUpTimeLine()
        }

    }

    override fun onNewIntent(intent: Intent?) {
        println(intent)
        super.onNewIntent(intent)
        val uri = intent!!.data
        if(uri != null && uri.toString().startsWith(TWITTER_CALLBACK_URL)) {
            val verifier = uri.getQueryParameter(URL_TWITTER_OAUTH_VERIFIER)
            try {
                Thread {
                    val accessToken = twitter!!.getOAuthAccessToken(requestToken, verifier)
                    sharedPreference!!.edit()
                        .putString(PREF_KEY_OAUTH_TOKEN, accessToken.token)
                        .putString(PREF_KEY_OAUTH_SECRET, accessToken.tokenSecret)
                        .putBoolean(PREF_KEY_TWITTER_LOGIN, true)
                        .commit()
                    setUpTimeLine()
                }.start()
            } catch (e : Exception) {
                Log.e(TAG, "Twitter Login Error $e" )
            }
        }
    }

    fun isUserLoggedInAlready() : Boolean {
        return sharedPreference!!.getBoolean(PREF_KEY_TWITTER_LOGIN,false)
    }

    fun setUpTimeLine() {
        val intent = Intent(this,TimeLineActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    fun loginToTwitter() {
        twitter = TwitterFactory.getSingleton()
        twitter!!.setOAuthConsumer(TWITTER_CONSUMER_KEY, TWITTER_CONSUMER_KEY_SECRET)
        try {
            Thread{
                requestToken = twitter!!.getOAuthRequestToken(TWITTER_CALLBACK_URL)
                val url = requestToken!!.authenticationURL
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
            }.start()

        } catch (e : Exception) {
            println(e.message)
            e.printStackTrace()
        }

    }
}
