package com.mprechl.cocktailapp.network

import com.mprechl.cocktailapp.model.CocktailData
import com.mprechl.cocktailapp.model.IngredientData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CocktailApi {
    @GET("/api/json/v1/1/search.php")
    fun fetchCocktails(
        @Query("s") searchTerm: String?
    ): Call<CocktailData?>?

    @GET("/api/json/v1/1/lookup.php")
    fun fetchCocktailDetails(
        @Query("i") id: String?
    ): Call<CocktailData?>?

    @GET("/api/json/v1/1/search.php")
    fun fetchIngredients(
        @Query("i") searchTerm: String?
    ): Call<IngredientData?>?

    @GET("/api/json/v1/1/lookup.php")
    fun fetchIngredientDetails(
        @Query("iid") id: String?
    ): Call<IngredientData?>?
}