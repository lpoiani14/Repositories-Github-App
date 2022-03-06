package com.leonardo.repositoriesgithub.ui

import android.content.Context
import android.content.Intent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.Menu
import androidx.appcompat.widget.SearchView
import com.leonardo.repositoriesgithub.R
import com.leonardo.repositoriesgithub.core.createDialog
import com.leonardo.repositoriesgithub.core.createProgressDialog
import com.leonardo.repositoriesgithub.core.hideSoftKeyboard
import com.leonardo.repositoriesgithub.data.model.Repo
import com.leonardo.repositoriesgithub.databinding.ActivityMainBinding
import com.leonardo.repositoriesgithub.presentation.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private val dialog by lazy { createProgressDialog() }
    private val adapter by lazy { RepoListAdapter() }
    private val viewModel by viewModel<MainViewModel>()
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        binding.rvReposList.adapter = adapter

        viewModel.repos.observe(this) {
            when (it) {
                is MainViewModel.State.Error -> {
                    dialog.dismiss()
                    createDialog {
                        setMessage(it.error.message)
                    }.show()

                }
                MainViewModel.State.Loading -> {
                    dialog.show()
                }
                is MainViewModel.State.Success -> {
                    dialog.dismiss()
                    adapter.submitList(it.list)
                }
            }
        }
        insertListener()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val searchView = menu.findItem(R.id.action_search).actionView as SearchView
        searchView.setOnQueryTextListener(this)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let { viewModel.getRepoList(it) }
        Log.e(TAG, "onQueryTextSubmit: $query")
        binding.root.hideSoftKeyboard()
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        Log.e(TAG, "onQueryTextChange: $newText")
        return false
    }

    companion object {
        private const val TAG = "onQuery"
    }

    private fun insertListener() {
        adapter.listenerRepo = { repo -> repoDetailsIntent(this@MainActivity,repo)

        }
    }
    private fun repoDetailsIntent(context: Context, repo: Repo){
        val intent = Intent(context, RepoDetails::class.java)
        intent.putExtra(RepoDetails.Extras.REPO,repo)
        startActivity(intent)
    }
}