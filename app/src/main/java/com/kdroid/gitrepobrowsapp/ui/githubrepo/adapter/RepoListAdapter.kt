package com.kdroid.gitrepobrowsapp.ui.githubrepo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kdroid.gitrepobrowsapp.data.RepositoryDTO
import com.kdroid.gitrepobrowsapp.databinding.ItemRepoListBinding
import com.kdroid.gitrepobrowsapp.utils.CircleTransform
import com.squareup.picasso.Picasso
import timber.log.Timber

class RepoListAdapter(
    val repoList: List<RepositoryDTO>,
    private val onRepoPress: (RepositoryDTO, View) -> Unit,
) : RecyclerView.Adapter<RepoListAdapter.ViewHolder>() {


    inner class ViewHolder(private val itemViewBinding: ItemRepoListBinding) :
        RecyclerView.ViewHolder(itemViewBinding.root) {
        fun bind(data: RepositoryDTO) {
            itemViewBinding.tvOrgName.text = data.name
            itemViewBinding.tvRepoOwnerName.text = data.owner?.login
            itemViewBinding.tvLanguageName.text = data.language
            itemViewBinding.tvStarCount.text = data.stargazers_count.toString()

            itemViewBinding.tvIssuesCount.text = buildString {
                append("Open Issues: ")
                append(data.open_issues_count)
            }
            itemViewBinding.tvDescription.text = data.description

            Picasso.get().load(data.owner?.avatar_url).transform(CircleTransform())
                .into(itemViewBinding.ivUserImage)

            itemView.setOnClickListener { onRepoPress.invoke(data, itemView) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemRepoBinding =
            ItemRepoListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemRepoBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(repoList[position])
    }

    override fun getItemCount() = repoList.size

}