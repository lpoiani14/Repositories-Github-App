package com.leonardo.repositoriesgithub.data.repositories


import com.leonardo.repositoriesgithub.core.RemoteException
import com.leonardo.repositoriesgithub.data.services.GitHubService
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class RepoRepositoryImpl(private val service : GitHubService) : RepoRepository {
    override suspend fun listRepositories(user: String) = flow {
        try {
            val repoList = service.listRepos(user)
            emit(repoList)
        } catch (e : HttpException){
            throw RemoteException(e.message ?: "Não foi possível fazer a busca no momento.")
        }
    }
}