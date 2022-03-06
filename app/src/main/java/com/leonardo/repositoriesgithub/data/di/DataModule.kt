package com.leonardo.repositoriesgithub.data.di

import android.util.Log
import com.google.gson.GsonBuilder
import com.leonardo.repositoriesgithub.data.repositories.RepoRepository
import com.leonardo.repositoriesgithub.data.repositories.RepoRepositoryImpl
import com.leonardo.repositoriesgithub.data.services.GitHubService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object DataModule {
    private const val OK_HTTP = "OkHttp"

    fun load(){
        loadKoinModules(networkModules()+repositoryModules())
    }

    private fun networkModules():Module{
        return module {
            single {

                val interceptor = HttpLoggingInterceptor {
                    Log.e(OK_HTTP, it)
                }
                interceptor.level = HttpLoggingInterceptor.Level.BODY

                OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .build()
            }

            single {
                GsonConverterFactory.create(GsonBuilder().create())
            }
            single {
                createService<GitHubService>(get(),get())
            }
        }
    }

    private fun repositoryModules():Module{
        return module {
            single<RepoRepository> {
                RepoRepositoryImpl(get())
            }
        }
    }

    private inline fun <reified T> createService(okHttpClient: OkHttpClient, factory: GsonConverterFactory) : T {
        var retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .client(okHttpClient)
            .addConverterFactory(factory)
            .build()
        return retrofit.create(T::class.java)
    }

}