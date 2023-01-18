package com.kdroid.gitrepobrowsapp.ui

import android.os.Bundle
import android.system.Os.bind
import androidx.appcompat.app.AppCompatActivity
import com.kdroid.common.extensions.viewBindings
import com.kdroid.gitrepobrowsapp.GitApplication
import com.kdroid.gitrepobrowsapp.R
import com.kdroid.gitrepobrowsapp.databinding.ActivityMainBinding
import com.kdroid.gitrepobrowsapp.di.subcomponent.DashboardComponent
import com.kdroid.gitrepobrowsapp.ui.githubrepo.github_repo.GithubRepoFragment
import com.kdroid.gitrepobrowsapp.utils.getAppComponent

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var dashboardComponent: DashboardComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        dashboardComponent =  getAppComponent().addDashboardComponent().create()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, GithubRepoFragment.newInstance())
                .commitNow()
        }
    }


}