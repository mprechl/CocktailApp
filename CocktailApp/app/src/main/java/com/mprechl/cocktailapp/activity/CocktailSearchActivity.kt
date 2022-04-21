package com.mprechl.cocktailapp.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.mprechl.cocktailapp.adapter.CocktailAdapter
import com.mprechl.cocktailapp.databinding.ActivityCocktailSearchBinding
import com.mprechl.cocktailapp.model.Cocktail
import com.mprechl.cocktailapp.model.CocktailData
import com.mprechl.cocktailapp.network.NetworkManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CocktailSearchActivity : AppCompatActivity(), CocktailAdapter.OnCocktailSelectedListener {
    lateinit var binding: ActivityCocktailSearchBinding

    private lateinit var adapter: CocktailAdapter
    private var cocktails: MutableList<Cocktail> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCocktailSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                fetchCocktailsList(query)
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
        })

    }

    private fun fetchCocktailsList(query: String?) {
        val call = NetworkManager.fetchCocktails(query?: "")
        call?.enqueue(object: Callback<CocktailData?> {
            override fun onResponse(call: Call<CocktailData?>, response: Response<CocktailData?>) {
                // no usable data received
                if (!response.isSuccessful
                    || response.body() == null
                    || response.body()?.drinks == null) {
                    clear()
                    return
                }

                // usable data received
                clear()
                val drinks = response.body()!!.drinks!!
                cocktails.addAll(drinks)
                adapter.notifyItemRangeInserted(0, drinks.size)
            }

            override fun onFailure(call: Call<CocktailData?>, t: Throwable) {
                Log.e(TAG, "Error: " + t.localizedMessage)
            }

            private fun clear() {
                val l = cocktails.size
                cocktails.clear()
                adapter.notifyItemRangeRemoved(0, l)
            }
        })
    }

    private fun initRecyclerView() {
        adapter = CocktailAdapter(cocktails, this)
        binding.recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        binding.recyclerView.adapter = adapter
    }

    companion object {
        private val TAG = CocktailSearchActivity::class.java.simpleName
    }

    override fun onCocktailSelected(cocktailId: String?) {
        // navigate to cocktail details
        val cocktailDetailsIntent = Intent(this, CocktailDetailsActivity::class.java)
        cocktailDetailsIntent.putExtra("id", cocktailId)
        startActivity(cocktailDetailsIntent)
    }
}