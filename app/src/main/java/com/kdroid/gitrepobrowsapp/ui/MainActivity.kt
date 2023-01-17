package com.kdroid.gitrepobrowsapp.ui

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.kdroid.common.extensions.viewBindings
import com.kdroid.gitrepobrowsapp.GitApplication
import com.kdroid.gitrepobrowsapp.R
import com.kdroid.gitrepobrowsapp.databinding.ActivityMainBinding
import com.kdroid.gitrepobrowsapp.di.subcomponent.DashboardComponent
import com.kdroid.gitrepobrowsapp.ui.githubrepo.github_repo.GithubRepoFragment
import timber.log.Timber


class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val binding by viewBindings(ActivityMainBinding::bind)
    lateinit var dashboardComponent: DashboardComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        dashboardComponent =
            (this.application as GitApplication).applicationComponent.addDashboardComponent()
                .create()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, GithubRepoFragment.newInstance()).commitNow()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.home_menu, menu)
        configureSearch(menu)
        return super.onCreateOptionsMenu(menu);
    }

    private fun configureSearch(menu: Menu?) {
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = (menu?.findItem(R.id.item_search)?.actionView as SearchView)
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        makeSearchQuery(searchView)
    }

    private fun makeSearchQuery(searchView: SearchView) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Timber.d("onQuery text submit $query")
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Timber.d("onQuery text $newText")
                return false
            }

        })
    }
}