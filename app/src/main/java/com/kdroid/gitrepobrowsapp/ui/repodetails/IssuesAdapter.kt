package com.kdroid.gitrepobrowsapp.ui.repodetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kdroid.gitrepobrowsapp.data.IssuesModel
import com.kdroid.gitrepobrowsapp.databinding.ItemIssuesListBinding
import com.kdroid.gitrepobrowsapp.utils.CircleTransform
import com.squareup.picasso.Picasso

class IssuesAdapter(
    private val issuesList: List<IssuesModel>,
) : RecyclerView.Adapter<IssuesAdapter.IssuesViewHolder>() {

    class IssuesViewHolder(private val itemBinding: ItemIssuesListBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(data: IssuesModel) {
            itemBinding.tvIssueName.text = data.title
            itemBinding.tvIssueBody.text = data.body

            Picasso.get().load(data.user?.avatar_url).transform(CircleTransform())
                .into(itemBinding.ivUserImage)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IssuesViewHolder {
        val view: ItemIssuesListBinding =
            ItemIssuesListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IssuesViewHolder(view)
    }

    override fun getItemCount(): Int = issuesList.size

    override fun onBindViewHolder(holder: IssuesViewHolder, position: Int) {
        holder.bind(issuesList[position])
    }
}