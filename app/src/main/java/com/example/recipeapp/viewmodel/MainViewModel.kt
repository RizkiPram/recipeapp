package com.example.recipeapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recipeapp.data.local.entity.MealEntity
import com.example.recipeapp.data.remote.api.ApiConfig
import com.example.recipeapp.data.remote.response.CategoriesItem
import com.example.recipeapp.data.remote.response.CategoryResponse
import com.example.recipeapp.data.remote.response.MealsItem
import com.example.recipeapp.data.remote.response.RecipeResponse
import com.example.recipeapp.repository.MealRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val mealRepository: MealRepository):ViewModel() {
    private val _listCategory = MutableLiveData<List<CategoriesItem>>()
    val listCategory:MutableLiveData<List<CategoriesItem>> = _listCategory
//    private val _listRecipe = MutableLiveData<List<MealsItem>>()
//    val listRecipe:MutableLiveData<List<MealsItem>> = _listRecipe
    fun getCategoryRecipe(){
        val client=ApiConfig.getApiService().getListCategories()
        client.enqueue(object : Callback<CategoryResponse>{
            override fun onResponse(
                call: Call<CategoryResponse>,
                response: Response<CategoryResponse>
            ) {
                val responseBody=response.body()
                if (response.isSuccessful){
                    if (responseBody != null){
                        listCategory.value=responseBody.categories
                    }
                }else{
                    Log.d("fail",response.message())
                }
            }

            override fun onFailure(call: Call<CategoryResponse>, t: Throwable) {
                t.message?.let { Log.d("onFailure", it) }
            }
        })
    }
    fun deleteMeal()=mealRepository.deleteMeals()
    fun searchMeal(query: String)=mealRepository.searchMeals(query)
    fun getMeals(c:String)=mealRepository.getListMeal(c)
//    fun getRecipe(c:String){
//        val client=ApiConfig.getApiService().getListRecipe(c)
//        client.enqueue(object :Callback<RecipeResponse>{
//            override fun onResponse(
//                call: Call<RecipeResponse>,
//                response: Response<RecipeResponse>
//            ) {
//                val responseBody=response.body()
//                if (response.isSuccessful){
//                    if (responseBody!=null){
//                        listRecipe.value=responseBody.meals
//                    }
//                }else{
//                    Log.d("recipe","fail ${response.message()}")
//                }
//            }
//
//            override fun onFailure(call: Call<RecipeResponse>, t: Throwable) {
//                Log.d("onFailure","onFailure:${t.message}")
//            }
//        })
//    }
}