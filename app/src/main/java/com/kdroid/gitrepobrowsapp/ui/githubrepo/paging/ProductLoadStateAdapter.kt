package com.kdroid.gitrepobrowsapp.ui.githubrepo.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kdroid.gitrepobrowsapp.databinding.ItemLoadingStateBinding
import com.kdroid.gitrepobrowsapp.utils.visible
import timber.log.Timber

class ProductLoadStateAdapter(
    private val retry: () -> Unit,
) : LoadStateAdapter<ProductLoadStateAdapter.LoadViewHolder>() {

    inner class LoadViewHolder(
        private val binding: ItemLoadingStateBinding,
        private val retry: () -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Error) {
                binding.tvItemCount.text = "No More Data"
                Timber.d("Error  state")
                // binding.progressBar.hide()
            }

            binding.progressBar.visible(loadState is LoadState.Loading)
            binding.tvItemCount.visible(loadState is LoadState.Error)

            // TODO impl later
            binding.buttonRetry.setOnClickListener {
                //  retry()
            }

        }
    }

    override fun onBindViewHolder(holder: LoadViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState,
    ) = LoadViewHolder(
        ItemLoadingStateBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ), retry
    )
}

