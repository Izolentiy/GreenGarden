package org.android.learning.sunflower.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import org.android.learning.sunflower.databinding.ListItemFooterBinding as Binding

class StateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<StateAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: Binding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.buttonRetryAgain.setOnClickListener { retry.invoke() }
        }

        fun bind(loadState: LoadState) = with(binding) {
            if (loadState is LoadState.Error) {
                val errorTarget = ErrorTarget(
                    textViewMessageError,
                    textViewDetailError,
                    buttonRetryAgain,
                    root.context
                )
                handleError(loadState.error, errorTarget)
            }
            progressbarPhotoItem.isVisible = loadState is LoadState.Loading
            buttonRetryAgain.isVisible = loadState is LoadState.Error
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState) = ViewHolder(
        Binding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

}