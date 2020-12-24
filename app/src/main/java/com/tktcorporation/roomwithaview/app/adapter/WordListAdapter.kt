package com.tktcorporation.roomwithaview.app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tktcorporation.roomwithaview.R
import com.tktcorporation.roomwithaview.infrastructure.entity.WordEntity
import com.tktcorporation.roomwithaview.app.adapter.WordListAdapter.WordViewHolder
import com.tktcorporation.roomwithaview.app.viewmodel.WordViewModel
import com.tktcorporation.roomwithaview.databinding.RecyclerviewItemBinding

class WordListAdapter(
    private val viewLifecycleOwner: LifecycleOwner,
    private val viewModel: WordViewModel
) : ListAdapter<WordEntity, WordViewHolder>(WORDS_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return WordViewHolder(RecyclerviewItemBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current, viewLifecycleOwner, viewModel)
    }

    class WordViewHolder(private val binding: RecyclerviewItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: WordEntity, viewLifecycleOwner: LifecycleOwner, viewModel: WordViewModel) {
            binding.run {
                lifecycleOwner = viewLifecycleOwner
                word = item
                this.viewModel = viewModel

                executePendingBindings()
            }
        }
    }

    companion object {
        private val WORDS_COMPARATOR = object : DiffUtil.ItemCallback<WordEntity>() {
            override fun areItemsTheSame(oldItem: WordEntity, newItem: WordEntity): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: WordEntity, newItem: WordEntity): Boolean {
                return oldItem.word == newItem.word
            }
        }
    }
}
