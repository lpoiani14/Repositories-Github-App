package com.leonardo.repositoriesgithub.ui

import com.bumptech.glide.Glide
import com.leonardo.repositoriesgithub.data.model.Repo
import com.leonardo.repositoriesgithub.databinding.ActivityRepoDetailsBinding

class RepoDetailsAdapter {
    fun bind(item: Repo, binding: ActivityRepoDetailsBinding){
        binding.toolbarRepoDetails.title = item.name
        binding.chStar.text = item.stargazersCount.toString()
        binding.tvDescription.text = item.description
        binding.tvLanguage.text = item.language
        binding.tvOwnerName.text = item.owner?.login

        Glide.with(binding.root.context)
            .load(item.owner?.avatarURL)
            .into(binding.ivAvatar)
    }
}