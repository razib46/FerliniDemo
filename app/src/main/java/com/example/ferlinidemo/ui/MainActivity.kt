package com.example.ferlinidemo.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ferlinidemo.databinding.ActivityMainBinding
import com.example.ferlinidemo.responses.Stargazer
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ferlinidemo.ApiEndpointInterface
import com.example.ferlinidemo.Application
import com.example.ferlinidemo.adapters.StargazerAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import com.example.ferlinidemo.R
import okhttp3.OkHttpClient
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private var loadingSpinner: ProgressBar? = null
    private var listView: RecyclerView? = null
    private var dataset: List<Stargazer>? = null
    private var stargazerAdapter: RecyclerView.Adapter<*>? = null
    private var filterDialog: FilterDialog? = null

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        loadingSpinner = findViewById(R.id.loadingSpinner)
        listView = findViewById(R.id.recycler_view)
    }

    override fun onStart() {
        super.onStart()
        openFilterModal()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> {
                openFilterModal()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun openFilterModal() {
        if (filterDialog == null) {
            filterDialog = FilterDialog()
        }
        filterDialog?.isCancelable = true
        filterDialog?.show(supportFragmentManager, getString(R.string.filter_title))
    }

    private fun createRetrofit(): Retrofit {
        val httpClient = OkHttpClient.Builder()
        return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(Application.endpoint).client(httpClient.build()).build()
    }

    private fun showSpinner() {
        loadingSpinner?.visibility = View.VISIBLE
    }

    private fun hideSpinner() {
        loadingSpinner?.visibility = View.GONE
    }

    fun loadStargazers(owner: String, repo: String) {
        showSpinner()
        val retrofit: Retrofit = createRetrofit()
        val apiEndpointInterface: ApiEndpointInterface = retrofit.create(ApiEndpointInterface::class.java)
        val stargazers: Call<List<Stargazer>> = apiEndpointInterface.getStargazers(owner, repo)

        stargazers.enqueue(object : Callback<List<Stargazer>> {
            override fun onResponse(call: Call<List<Stargazer>>?, response: Response<List<Stargazer>>?) {
                if (response?.body() != null) {
                    dataset = response.body()
                } else {
                    dataset = ArrayList()
                    Toast.makeText(this@MainActivity, getString(R.string.no_result_found), Toast.LENGTH_SHORT).show()
                }

                stargazerAdapter = StargazerAdapter(dataset!!)
                listView?.adapter = stargazerAdapter
                listView?.layoutManager = LinearLayoutManager(this@MainActivity)
                hideSpinner()
            }

            override fun onFailure(call: Call<List<Stargazer>>?, t: Throwable?) {
                hideSpinner()
                Toast.makeText(this@MainActivity, getString(R.string.generic_error), Toast.LENGTH_SHORT).show()
            }
        })
    }
}