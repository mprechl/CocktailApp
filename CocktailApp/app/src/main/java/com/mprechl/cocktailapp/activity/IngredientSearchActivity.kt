package com.mprechl.cocktailapp.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.mprechl.cocktailapp.adapter.IngredientAdapter
import com.mprechl.cocktailapp.databinding.ActivityIngredientSearchBinding
import com.mprechl.cocktailapp.model.Ingredient
import com.mprechl.cocktailapp.model.IngredientData
import com.mprechl.cocktailapp.network.NetworkManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class IngredientSearchActivity : AppCompatActivity(),
    IngredientAdapter.OnIngredientSelectedListener {
    lateinit var binding: ActivityIngredientSearchBinding

    private lateinit var adapter: IngredientAdapter
    private var ingredients: MutableList<Ingredient> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIngredientSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                fetchIngredientsList(query)
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
        })
    }

    private fun fetchIngredientsList(query: String?) {
        val call = NetworkManager.fetchIngredients(query?: "")
        call?.enqueue(object: Callback<IngredientData?> {
            override fun onResponse(call: Call<IngredientData?>, response: Response<IngredientData?>) {
                // no usable data received
                if (!response.isSuccessful
                    || response.body() == null
                    || response.body()?.ingredients == null) {
                    clear()
                    return
                }

                // usable data received
                clear()
                val res = response.body()!!.ingredients!!
                ingredients.addAll(res)
                adapter.notifyItemRangeInserted(0, res.size)
            }

            override fun onFailure(call: Call<IngredientData?>, t: Throwable) {
                Log.e(TAG, "Error: " + t.localizedMessage)
            }

            private fun clear() {
                val l = ingredients.size
                ingredients.clear()
                adapter.notifyItemRangeRemoved(0, l)
            }
        })
    }

    companion object {
        private val TAG = IngredientSearchActivity::class.java.simpleName
    }

    private fun initRecyclerView() {
        adapter = IngredientAdapter(ingredients, this)
        binding.recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        binding.recyclerView.adapter = adapter
    }

    override fun onIngredientSelected(ingredientId: String?) {
        // navigate to ingredient details
        val ingredientDetailsIntent = Intent(this, IngredientDetailsActivity::class.java)
        ingredientDetailsIntent.putExtra("id", ingredientId)
        startActivity(ingredientDetailsIntent)
    }
}