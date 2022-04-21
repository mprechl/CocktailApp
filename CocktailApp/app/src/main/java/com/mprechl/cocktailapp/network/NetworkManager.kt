package com.mprechl.cocktailapp.network

import com.mprechl.cocktailapp.model.CocktailData
import com.mprechl.cocktailapp.model.IngredientData
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkManager {
    private val retrofit: Retrofit
    private val cocktailApi: CocktailApi

    private const val SERVICE_URL = "https://www.thecocktaildb.com"

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(SERVICE_URL)
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        cocktailApi = retrofit.create(CocktailApi::class.java)
    }

    fun fetchCocktails(searchTerm: String?): Call<CocktailData?>? {
        return cocktailApi.fetchCocktails(searchTerm)
    }

    fun fetchCocktailDetails(id: String?): Call<CocktailData?>? {
        return cocktailApi.fetchCocktailDetails(id)
    }

    fun fetchIngredients(searchTerm: String?): Call<IngredientData?>? {
        return cocktailApi.fetchIngredients(searchTerm)
    }

    fun fetchIngredientDetails(id: String?): Call<IngredientData?>? {
        return cocktailApi.fetchIngredientDetails(id)
    }
}