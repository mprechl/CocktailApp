package com.mprechl.cocktailapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.mprechl.cocktailapp.adapter.CocktailAdapter
import com.mprechl.cocktailapp.adapter.IngredientAdapter
import com.mprechl.cocktailapp.databinding.ActivityMyIngredientsBinding
import com.mprechl.cocktailapp.model.FavCocktails
import com.mprechl.cocktailapp.model.MyIngredients

class MyIngredientsActivity : AppCompatActivity(),
    IngredientAdapter.OnIngredientSelectedListener {
    lateinit var binding: ActivityMyIngredientsBinding

    private lateinit var adapter: IngredientAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyIngredientsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()

    }

    private fun initRecyclerView() {
        adapter = IngredientAdapter(MyIngredients.ingredients, this)
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