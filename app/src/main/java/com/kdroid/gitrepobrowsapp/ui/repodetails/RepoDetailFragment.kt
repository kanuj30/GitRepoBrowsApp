package com.kdroid.gitrepobrowsapp.ui.repodetails

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.kdroid.common.ViewState
import com.kdroid.gitrepobrowsapp.R
import com.kdroid.gitrepobrowsapp.data.IssuesModel
import com.kdroid.gitrepobrowsapp.data.RepoDetails
import com.kdroid.gitrepobrowsapp.data.RepositoryDTO
import com.kdroid.gitrepobrowsapp.databinding.FragmentRepoDetailsBinding
import com.kdroid.gitrepobrowsapp.ui.MainActivity
import com.kdroid.gitrepobrowsapp.utils.CircleTransform
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class RepoDetailFragment : Fragment(R.layout.fragment_repo_details) {

    companion object {
        const val REPO_EXTRA_DATA = "data"
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var binding: FragmentRepoDetailsBinding
    private lateinit var repoData: RepositoryDTO
    private val issuesList = mutableListOf<IssuesModel>()


    private lateinit var viewModel: RepoDetailViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).dashboardComponent.inject(this)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentRepoDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments
        repoData = args?.getParcelable(REPO_EXTRA_DATA)!!
        Timber.d("repoData :  $repoData")
        viewModel = ViewModelProvider(this, viewModelFactory)[RepoDetailViewModel::class.java]
        populateViewData()
        observeLiveData()
    }

    override fun onStart() {
        Timber.d("onStart()")
        super.onStart()
        viewModel.getRepoDetailData(
            repoData.license?.url,
            repoData.issues_url?.replace("{/number}", "")
        )
    }

    private fun observeLiveData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.onRepoData.collect {
                    Timber.d("repo detail colleted data $it")
                    when (it) {
                        is ViewState.Success -> loadData(it.data)
                        is ViewState.Error -> errorState()
                        is ViewState.Loading -> loadingState()
                    }
                }
            }
        }
    }


    private fun loadData(date: RepoDetails) {
        val licenceData = date.repoLicenseData
        val issueListData = date.issues
        binding.tvLicenseConditionsTitle.visibility = View.VISIBLE
        binding.tvLicenseUsageTitle.visibility = View.VISIBLE


        binding.tvLicenseName.text = licenceData.name
        binding.tvLicenseDescription.text = licenceData.description

        licenceData.permissions?.apply {
            binding.tvLicenseUsage.text = "- ${this.joinToString("\n- ")}"
        }

        licenceData.conditions?.apply {
            binding.tvLicenseConditions.text = "- ${this.joinToString("\n- ")}"
        }

        if (issueListData.isNotEmpty()) {
            binding.tvIssuesTitle.visibility = View.VISIBLE
            binding.issuesDivider.visibility = View.VISIBLE
            binding.rvIssuesList.visibility = View.VISIBLE

            issuesList.clear()
            issuesList.addAll(issueListData)
            binding.rvIssuesList.layoutManager = LinearLayoutManager(requireActivity())
            binding.rvIssuesList.adapter = IssuesAdapter(issuesList)
        }
    }

    private fun populateViewData() {
        binding.repoInfo.tvOrgName.text = repoData.name
        binding.repoInfo.tvRepoOwnerName.text = repoData.owner?.login
        binding.repoInfo.tvIssuesCount.text = buildString {
            append(repoData.open_issues_count.toString())
            append(buildString {
                append(" : Issues")
            })
        }

        Picasso.get().load(repoData.owner?.avatar_url).transform(CircleTransform())
            .into(binding.repoInfo.ivUserImage)

        //Hide the details in case it is missing from the repository
        if (repoData.license?.url.isNullOrEmpty()) {
            binding.tvLicenseTitle.visibility = View.GONE
            binding.tvLicenseUsageTitle.visibility = View.GONE
            binding.tvLicenseConditionsTitle.visibility = View.GONE
            binding.viewLicenseDivider.visibility = View.GONE
        }
    }


    private fun errorState() {

    }

    private fun loadingState() {
    }


}