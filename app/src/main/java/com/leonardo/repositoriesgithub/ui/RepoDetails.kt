package com.leonardo.repositoriesgithub.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.leonardo.repositoriesgithub.data.model.Repo
import com.leonardo.repositoriesgithub.databinding.ActivityRepoDetailsBinding
import io.noties.markwon.Markwon
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.InputStreamReader
import java.net.URL

class RepoDetails : AppCompatActivity() {

    object Extras {
        const val REPO = "EXTRA_REPO"
    }

    private val binding by lazy { ActivityRepoDetailsBinding.inflate(layoutInflater) }
    private val adapter = RepoDetailsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbarRepoDetails)

        val repo = intent?.extras?.getParcelable(Extras.REPO) as Repo?

        if (repo != null) {
            adapter.bind(repo, binding)
            supportActionBar?.title = binding.toolbarRepoDetails.title
            runBlocking { launch { loadMarkdown(repo) } }
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private suspend fun loadMarkdown(repo: Repo) {
        val markwon = Markwon.create(this)

        Thread {
            val fullString = readUrl(repo.owner?.login, repo.name, repo.defaultBranch)
            runOnUiThread {
                val markdown = markwon.toMarkdown(fullString)
                markwon.setParsedMarkdown(binding.markdownView, markdown)
            }
        }.start()
    }

    private fun readUrl(user: String?, repoName: String?, defaultBranch: String?): String {
        var fullString: String? = ""
        try {
            val url =
                URL("https://raw.githubusercontent.com/$user/$repoName/$defaultBranch/README.md")
            val reader = BufferedReader(InputStreamReader(url.openStream()))
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                fullString += (line + "\n")
            }
            reader.close()
            return fullString.toString()

        } catch (e: FileNotFoundException) {
            throw FileNotFoundException(e.message)
        }


    }

}

