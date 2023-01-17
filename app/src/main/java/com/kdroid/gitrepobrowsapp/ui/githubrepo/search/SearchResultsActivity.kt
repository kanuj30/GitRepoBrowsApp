package com.kdroid.gitrepobrowsapp.ui.githubrepo.search

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.kdroid.common.extensions.toDeepString
import com.kdroid.common.extensions.viewBindings
import com.kdroid.gitrepobrowsapp.R
import com.kdroid.gitrepobrowsapp.databinding.ActivitySearchResultsBinding
import timber.log.Timber


class SearchResultsActivity : AppCompatActivity(R.layout.activity_search_results) {

    private val binding by viewBindings(ActivitySearchResultsBinding::bind)
    lateinit var adapter: ArrayAdapter<String>
    var data: MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // initialize data
        data.add("Apple")
        data.add("Banana")
        data.add("Grape")
        data.add("Orange")


        // set adapter
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, data)
        binding.listView.adapter = adapter

        // handle search query
        handleIntent(intent)

    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.let { handleIntent(it) }
    }

    private fun handleIntent(intent: Intent) {

        Timber.d("data ${intent.toDeepString()}")
        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query -> doMySearch(query) }
        }
    }

    private fun doMySearch(query: String) {
        Timber.d(" search query $query")
        data.clear()
        // Filter the data based on the query
        for (item in data) {
            if (item.lowercase().contains(query.lowercase())) data.add(item)
        }
        adapter.notifyDataSetChanged()
    }
}