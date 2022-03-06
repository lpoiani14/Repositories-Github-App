package com.leonardo.repositoriesgithub.domain

import com.leonardo.repositoriesgithub.core.UseCase
import com.leonardo.repositoriesgithub.data.model.Repo
import com.leonardo.repositoriesgithub.data.repositories.RepoRepository
import kotlinx.coroutines.flow.Flow

class ListUserRepositoriesUseCase(private val repository: RepoRepository)
    : UseCase<String, List<Repo>>(){

    override suspend fun execute(param: String): Flow<List<Repo>> {
        return repository.listRepositories(param)
    }
}