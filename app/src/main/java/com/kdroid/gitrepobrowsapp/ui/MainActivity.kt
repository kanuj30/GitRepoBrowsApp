package com.kdroid.gitrepobrowsapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kdroid.common.extensions.viewBindings
import com.kdroid.gitrepobrowsapp.R
import com.kdroid.gitrepobrowsapp.databinding.ActivityMainBinding
import com.kdroid.gitrepobrowsapp.ui.githubrepo.main.MainFragment

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val binding by viewBindings(ActivityMainBinding::bind)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }


}