package com.example.rajatme.minitwitter.activities

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.example.rajatme.minitwitter.R
import com.example.rajatme.minitwitter.databinding.ActivityBaseBinding
import android.content.Intent



class BaseActivity : AppCompatActivity() {
    var selectedFragment : Fragment = TimeLineFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding = DataBindingUtil.setContentView<ActivityBaseBinding>(this,R.layout.activity_base)
        var bottomNavigation = binding.bottomNavigation
        bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home ->
                    selectedFragment = TimeLineFragment()

                R.id.search ->
                    selectedFragment = SearchTweetFragment()

            }
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, selectedFragment!!).commit()
            true
        }
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, TimeLineFragment()).commit()
    }

    override fun onBackPressed() {
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container,selectedFragment).commit()
    }

    override fun onRestart() {
        super.onRestart()
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container,selectedFragment).commit()
    }

}