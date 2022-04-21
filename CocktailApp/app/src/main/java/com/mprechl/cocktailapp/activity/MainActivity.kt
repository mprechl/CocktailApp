package com.mprechl.cocktailapp.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mprechl.cocktailapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.menuOptSearchCocktail.setOnClickListener {
            val cocktailSearchIntent = Intent(this, CocktailSearchActivity::class.java)
            startActivity(cocktailSearchIntent)
        }

        binding.menuOptSearchIngredient.setOnClickListener {
            var ingredientSearchIntent = Intent(this, IngredientSearchActivity::class.java)
            startActivity(ingredientSearchIntent)
        }

        binding.menuOptFavCocktails.setOnClickListener {
            var favCocktailIntent = Intent(this, FavCocktailActivity::class.java)
            startActivity(favCocktailIntent)
        }

        binding.menuOptMyIngredients.setOnClickListener {
            var myIngredientsIntent = Intent(this, MyIngredientsActivity::class.java)
            startActivity(myIngredientsIntent)
        }
    }
}