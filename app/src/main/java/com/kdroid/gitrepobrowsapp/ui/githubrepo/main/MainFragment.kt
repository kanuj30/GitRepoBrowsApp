package com.kdroid.gitrepobrowsapp.ui.githubrepo.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kdroid.common.ViewState
import com.kdroid.common.extensions.viewBindings
import com.kdroid.gitrepobrowsapp.R
import com.kdroid.gitrepobrowsapp.api.ApiHelperImpl
import com.kdroid.gitrepobrowsapp.data.RepositoryDTO
import com.kdroid.gitrepobrowsapp.databinding.FragmentMainBinding
import com.kdroid.gitrepobrowsapp.network.ApiClient
import com.kdroid.gitrepobrowsapp.ui.githubrepo.adapter.RepoListAdapter
import com.kdroid.gitrepobrowsapp.ui.repo.GitRepository
import com.kdroid.gitrepobrowsapp.ui.repodetails.RepoDetailFragment
import com.kdroid.gitrepobrowsapp.viewmodelfactory.GitViewModelFactory
import timber.log.Timber

class MainFragment : Fragment(R.layout.fragment_main) {

    companion object {
        fun newInstance() = MainFragment()
    }

    private var repository: GitRepository = GitRepository()
    private lateinit var viewModel: MainViewModel
    private val binding by viewBindings(FragmentMainBinding::bind)
    private var repoList = mutableListOf<RepositoryDTO>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("onCreate()")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        Timber.d("onCreateView()")
        return inflater.inflate(R.layout.fragment_main, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("onViewCreated()")
        // val apiHelper = ApiHelperImpl(ApiClient.request!!)

        viewModel = ViewModelProvider(this,
            GitViewModelFactory(repository,
                ApiHelperImpl(ApiClient.request!!))).get(MainViewModel::class.java)

        viewModel.fetchRepoList()

        // perform action after click
        val repoAdapter = RepoListAdapter(repoList) { repoData, sharedView ->
            Timber.d("click")
            ViewCompat.setTransitionName(sharedView, "repoName")
            val fragment = RepoDetailFragment()
            val bundle = Bundle()
            bundle.putParcelable(RepoDetailFragment.REPO_EXTRA_DATA, repoData)
            fragment.arguments = bundle
            activity?.supportFragmentManager?.beginTransaction()
                ?.addSharedElement(sharedView, "repoName")
                ?.replace(R.id.container, fragment)?.addToBackStack(null)?.commit()
        }

        binding.repoList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = repoAdapter
        }

        binding.swipeRefresh.setOnRefreshListener { viewModel.fetchRepoList() }
        // observe the changes
        viewModel.state.observe(viewLifecycleOwner) { state ->
            Timber.d("Emitting data")
            when (state) {
                is ViewState.Success -> {
                    repoList.clear()
                    repoList.addAll(state.data.items.take(20).toMutableList())
                    repoAdapter.notifyDataSetChanged()
                }
                is ViewState.Loading -> loadingState()
                is ViewState.Error -> errorState()
            }
        }
    }

    private fun errorState() {

    }

    private fun loadingState() {
    }
}