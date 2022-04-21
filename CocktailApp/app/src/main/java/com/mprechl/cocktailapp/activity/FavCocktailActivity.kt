package com.mprechl.cocktailapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.mprechl.cocktailapp.adapter.CocktailAdapter
import com.mprechl.cocktailapp.databinding.ActivityFavCocktailBinding
import com.mprechl.cocktailapp.model.Cocktail
import com.mprechl.cocktailapp.model.FavCocktails

class FavCocktailActivity : AppCompatActivity(),
    CocktailAdapter.OnCocktailSelectedListener {
    lateinit var binding: ActivityFavCocktailBinding

    private lateinit var adapter: CocktailAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavCocktailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()

    }

    private fun initRecyclerView() {
        adapter = CocktailAdapter(FavCocktails.cocktails, this)
        binding.recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        binding.recyclerView.adapter = adapter
    }

    override fun onCocktailSelected(cocktailId: String?) {
        // navigate to cocktail details
        val cocktailDetailsIntent = Intent(this, CocktailDetailsActivity::class.java)
        cocktailDetailsIntent.putExtra("id", cocktailId)
        startActivity(cocktailDetailsIntent)
    }
}