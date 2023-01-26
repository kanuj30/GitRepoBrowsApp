package com.kdroid.gitrepobrowsapp.ui.githubrepo.github_repo

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.kdroid.common.extensions.DebouncingQueryTextListener
import com.kdroid.common.extensions.hide
import com.kdroid.common.extensions.show
import com.kdroid.gitrepobrowsapp.R
import com.kdroid.gitrepobrowsapp.databinding.FragmentMainBinding
import com.kdroid.gitrepobrowsapp.ui.MainActivity
import com.kdroid.gitrepobrowsapp.ui.githubrepo.adapter.RepoListAdapter
import com.kdroid.gitrepobrowsapp.ui.githubrepo.paging.GitRepoSearchPagingSource
import com.kdroid.gitrepobrowsapp.ui.githubrepo.paging.ProductLoadStateAdapter
import com.kdroid.gitrepobrowsapp.ui.repodetails.RepoDetailFragment
import com.kdroid.gitrepobrowsapp.viewmodelfactory.GitViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named

class GithubRepoFragment : Fragment(R.layout.fragment_main) {

    companion object {
        fun newInstance() = GithubRepoFragment()
    }

    @Inject
    lateinit var viewModelFactory: GitViewModelFactory

    //private val viewModel by viewModels { viewModelFactory }


    @Inject
    @Named("search_query")
    lateinit var searchQueryModule: String

    private lateinit var viewModel: GithubRepoViewModel
    lateinit var binding: FragmentMainBinding

    override fun onAttach(context: Context) {
        (activity as MainActivity).dashboardComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("onCreate()")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        Timber.d("onCreateView()")
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        Timber.d("onViewCreated()")
        viewModel = ViewModelProvider(this, viewModelFactory)[GithubRepoViewModel::class.java]


        // perform action after click
        val repoAdapter = RepoListAdapter() { repoData, sharedView ->
            Timber.d("click")
            ViewCompat.setTransitionName(sharedView, "repoName")
            val fragment = RepoDetailFragment()
            val bundle = Bundle()
            bundle.putParcelable(RepoDetailFragment.REPO_EXTRA_DATA, repoData)
            fragment.arguments = bundle
            activity?.supportFragmentManager?.beginTransaction()
                ?.addSharedElement(sharedView, "repoName")?.replace(R.id.container, fragment)
                ?.addToBackStack(null)?.commit()
        }

        binding.repoList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = repoAdapter
        }

        repoAdapter.addLoadStateListener { loadState ->
            // show empty list
            if (loadState.refresh is LoadState.Loading) {
                binding.progressBar.show()
                loadingState()
            } else {
                binding.progressBar.hide()
                val errorState = when {
                    loadState.append is LoadState.Error -> {
                        Timber.e("error state ")
                        loadState.append as LoadState.Error

                    }
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    else -> null
                }
                errorState?.let {
                    Timber.e("error in loading ${errorState.error.message}")
                    errorState()
                }

            }
        }

        // load more view
        binding.repoList.adapter =
            repoAdapter.withLoadStateHeaderAndFooter(header = ProductLoadStateAdapter { repoAdapter.retry() },
                footer = ProductLoadStateAdapter { repoAdapter.retry() })


        viewModel.searchData.observe(viewLifecycleOwner) {
            repoAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        // callback for collecting latest data
        lifecycleScope.launch {
            viewModel.fetchRepoList().collectLatest {
                repoAdapter.submitData(lifecycle, it)
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_menu, menu)
        configureSearch(menu)
    }

    private fun configureSearch(menu: Menu) {
        val mSearch = menu.findItem(R.id.item_search)
        val mSearchView = mSearch.actionView as SearchView
        makeSearchQuery(mSearchView)
    }

    private fun makeSearchQuery(searchView: SearchView) {
        searchView.setOnQueryTextListener(DebouncingQueryTextListener(
            lifecycle, 500L
        ) { newText ->
            newText?.let {
                if (it.isEmpty()) viewModel.resetSearch() else viewModel.searchQueryChanged(it)
            }
        })
    }

    private fun errorState() {
        binding.progressBar.hide()
        binding.lottieAnimation.show()
        binding.lottieAnimation.playAnimation()
    }

    private fun loadingState() {
        binding.lottieAnimation.hide()
        binding.lottieAnimation.cancelAnimation()
        binding.progressBar.show()
    }
}