package com.example.recipeapp.data.remote.api

import com.example.recipeapp.data.remote.response.CategoryResponse
import com.example.recipeapp.data.remote.response.RecipeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("categories.php")
    fun getListCategories(): Call<CategoryResponse>

    @GET("filter.php")
    fun getListRecipe(
        @Query("c") c:String?
    ):Call<RecipeResponse>

}