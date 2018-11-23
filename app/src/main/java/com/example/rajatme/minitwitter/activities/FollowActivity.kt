package com.example.rajatme.minitwitter.activities

import android.app.AlertDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.example.rajatme.minitwitter.R
import com.example.rajatme.minitwitter.adapters.UserFollowerAdapter
import com.example.services.UserhomepageService
import com.example.services.Utils.NetworkState

abstract class FollowActivity : AppCompatActivity() {
    protected var recyclerView : RecyclerView ?= null
    protected var userhomepageService = UserhomepageService()
    protected lateinit var screenName : String
    protected var recyclerViewAdapter = UserFollowerAdapter()
    protected var dialog : AlertDialog ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        val builder = android.app.AlertDialog.Builder(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_followers)
        builder.setCancelable(false)
        builder!!.setView(R.layout.layout_loading_dialog)
        dialog = builder.create()
        recyclerView = findViewById(R.id.recycler_view)
        screenName = intent.getStringExtra("name")
        var itemDecorator = DividerItemDecoration(this, RecyclerView.VERTICAL)
        recyclerView!!.addItemDecoration(itemDecorator)
        recyclerView!!.adapter = recyclerViewAdapter
        recyclerView!!.layoutManager = LinearLayoutManager(this)
        getInfoaboutUser(screenName)
    }

    abstract fun getInfoaboutUser(screenName : String)

    fun setProgressDialog(networkState: NetworkState?) {
        if(networkState == NetworkState.LOADED)
            dialog!!.dismiss()
        else if(networkState == NetworkState.LOADING)
            dialog!!.show()
        else if(networkState?.status == NetworkState.Status.FAILED) {
            Toast.makeText(this,"There was an error fetching the results retry later", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}