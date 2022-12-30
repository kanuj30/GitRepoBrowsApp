package com.kdroid.gitrepobrowsapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kdroid.common.extensions.viewBindings
import com.kdroid.gitrepobrowsapp.GitApplication
import com.kdroid.gitrepobrowsapp.R
import com.kdroid.gitrepobrowsapp.databinding.ActivityMainBinding
import com.kdroid.gitrepobrowsapp.di.subcomponent.DashboardComponent
import com.kdroid.gitrepobrowsapp.ui.githubrepo.github_repo.GithubRepoFragment

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val binding by viewBindings(ActivityMainBinding::bind)
    lateinit var dashboardComponent: DashboardComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        dashboardComponent =   (this.application as GitApplication).applicationComponent.addDashboardComponent().create()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, GithubRepoFragment.newInstance())
                .commitNow()
        }
    }


}