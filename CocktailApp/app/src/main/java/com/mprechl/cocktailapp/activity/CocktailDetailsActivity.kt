package com.mprechl.cocktailapp.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.mprechl.cocktailapp.databinding.ActivityCocktailDetailsBinding
import com.mprechl.cocktailapp.model.Cocktail
import com.mprechl.cocktailapp.model.CocktailData
import com.mprechl.cocktailapp.model.FavCocktails
import com.mprechl.cocktailapp.network.NetworkManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CocktailDetailsActivity : AppCompatActivity() {
    lateinit var binding: ActivityCocktailDetailsBinding
    lateinit var drink: Cocktail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCocktailDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fetchCocktailDetails(intent.extras?.get("id") as String?)

        binding.button.setOnClickListener {
            if (!FavCocktails.cocktails.contains(drink))
                FavCocktails.cocktails.add(drink)
        }
    }

    private fun fetchCocktailDetails(cocktailId: String?) {
        NetworkManager.fetchCocktailDetails(cocktailId)?.enqueue(object: Callback<CocktailData?> {
            override fun onResponse(call: Call<CocktailData?>, response: Response<CocktailData?>) {
                // no usable data received
                if (!response.isSuccessful
                    || response.body() == null
                    || response.body()?.drinks == null) {
                    return
                }

                // usable data received
                drink = response.body()!!.drinks!![0]

                binding.nameTextView.text = drink.strDrink
                binding.descTextView.text = drink.strInstructions

                binding.n1.text = drink.strIngredient1
                binding.n2.text = drink.strIngredient2
                binding.n3.text = drink.strIngredient3
                binding.n4.text = drink.strIngredient4
                binding.n5.text = drink.strIngredient5

                binding.m1.text = drink.strMeasure1
                binding.m2.text = drink.strMeasure2
                binding.m3.text = drink.strMeasure3
                binding.m4.text = drink.strMeasure4
                binding.m5.text = drink.strMeasure5

                Glide.with(this@CocktailDetailsActivity)
                    .load(drink.strDrinkThumb)
                    .into(binding.imageView)

            }

            override fun onFailure(call: Call<CocktailData?>, t: Throwable) {
                Log.e(TAG, "Error: " + t.localizedMessage)
            }
        })
    }

    companion object {
        private val TAG = CocktailDetailsActivity::class.java.simpleName
    }
}