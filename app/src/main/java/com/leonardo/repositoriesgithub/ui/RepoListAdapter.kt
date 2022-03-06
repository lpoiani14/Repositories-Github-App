package com.leonardo.repositoriesgithub.ui


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.leonardo.repositoriesgithub.data.model.Repo
import com.leonardo.repositoriesgithub.databinding.ItemRepoBinding

class RepoListAdapter : ListAdapter<Repo, RepoListAdapter.ViewHolder>(DiffCallback()) {

    var listenerRepo: (Repo) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoListAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRepoBinding.inflate(inflater,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RepoListAdapter.ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: ItemRepoBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Repo){
            binding.tvRepoName.text = item.name
            binding.chStar.text = item.stargazersCount.toString()
            binding.tvDescription.text = item.description
            binding.tvLanguage.text = item.language
            binding.itemRepo.setOnClickListener { listenerRepo(item) }

            Glide.with(binding.root.context)
                .load(item.owner?.avatarURL)
                .into(binding.ivAvatar)
        }

    }
}

class DiffCallback : DiffUtil.ItemCallback<Repo>() {
    override fun areItemsTheSame(oldItem: Repo, newItem: Repo): Boolean = oldItem == newItem
    override fun areContentsTheSame(oldItem: Repo, newItem: Repo): Boolean = oldItem.id == newItem.id

}