package com.leonardo.repositoriesgithub.data.repositories

import com.leonardo.repositoriesgithub.data.model.Repo
import kotlinx.coroutines.flow.Flow

interface RepoRepository {
    suspend fun listRepositories(user : String) : Flow<List<Repo>>
}