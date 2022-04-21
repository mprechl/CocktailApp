package com.mprechl.cocktailapp.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.mprechl.cocktailapp.databinding.ActivityIngredientDetailsBinding
import com.mprechl.cocktailapp.model.Ingredient
import com.mprechl.cocktailapp.model.IngredientData
import com.mprechl.cocktailapp.model.MyIngredients
import com.mprechl.cocktailapp.network.NetworkManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class IngredientDetailsActivity : AppCompatActivity() {
    lateinit var binding: ActivityIngredientDetailsBinding

    lateinit var ingredient: Ingredient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIngredientDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fetchIngredientDetails(intent.extras?.get("id") as String?)

        binding.button.setOnClickListener {
            if (!MyIngredients.ingredients.contains(ingredient))
                MyIngredients.ingredients.add(ingredient)
        }
    }

    private fun fetchIngredientDetails(ingredientId: String?) {
        NetworkManager.fetchIngredientDetails(ingredientId)?.enqueue(
            object: Callback<IngredientData?> {
                override fun onResponse(
                    call: Call<IngredientData?>,
                    response: Response<IngredientData?>
                ) {
                    // no usable data received
                    if (!response.isSuccessful
                        || response.body() == null
                        || response.body()?.ingredients == null) {
                        return
                    }

                    // usable data received
                    ingredient = response.body()!!.ingredients!![0]

                    binding.nameTextView.text = ingredient.strIngredient
                    binding.descTextView.text = ingredient.strDescription
                }

                override fun onFailure(call: Call<IngredientData?>, t: Throwable) {
                    Log.e(TAG, "Error: " + t.localizedMessage)
                }

            }
        )
    }

    companion object {
        private val TAG = IngredientDetailsActivity::class.java.simpleName
    }
}