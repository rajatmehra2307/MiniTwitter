package com.example.rajatme.minitwitter

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView

class Hashtag : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hashtag)
        var textView = findViewById<TextView>(R.id.hashtag)
        var name = intent.getStringExtra("name")
        textView.setText("inside hashtag activity ${name}")
    }
}