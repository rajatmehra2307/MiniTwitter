package com.example.rajatme.minitwitter

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView

class MentionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mention)
        var textView = findViewById<TextView>(R.id.mention)
        var name = intent.getStringExtra("name")
        textView.setText("inside mention activity ${name}")
    }
}