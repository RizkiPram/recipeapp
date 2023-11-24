package com.example.recipeapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.recipeapp.data.local.entity.FavouriteEntity
import com.example.recipeapp.data.local.entity.MealEntity
import com.example.recipeapp.data.local.room.MealsDao
import com.example.recipeapp.data.remote.api.ApiService
import com.example.recipeapp.data.remote.response.RecipeResponse
import com.example.recipeapp.utils.AppExecutors
import com.example.recipeapp.utils.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealRepository private constructor(
    private val apiService: ApiService,
    private val mealsDao: MealsDao,
    private val appExecutors: AppExecutors
) {
    private val result = MediatorLiveData<Result<List<MealEntity>>>()
    fun getListMeal(c: String): LiveData<Result<List<MealEntity>>> {
        result.value = Result.Loading
        val client = apiService.getListRecipe(c)
        client.enqueue(object : Callback<RecipeResponse> {
            override fun onResponse(
                call: Call<RecipeResponse>, response: Response<RecipeResponse>
            ) {
                if (response.isSuccessful) {
                    val meal = response.body()?.meals
                    val mealsArray = ArrayList<MealEntity>()
                    appExecutors.diskIO.execute {
                        meal?.forEach { meals ->
                            val mealsItem = MealEntity(
                                meals.strMeal, meals.idMeal, meals.strMealThumb
                            )
                            mealsArray.add(mealsItem)
                        }
                        mealsDao.insertMeals(mealsArray)
                    }
                }
            }

            override fun onFailure(call: Call<RecipeResponse>, t: Throwable) {
                result.value = Result.Error(t.message.toString())
            }
        })
        val localData = mealsDao.getMeals()
        result.addSource(localData) { newData: List<MealEntity> ->
            result.value = Result.Success(newData)
        }
        return result
    }
    fun searchMeals(query: String): LiveData<List<MealEntity>> {
        return mealsDao.search(query)
    }
    fun deleteMeals(){
        CoroutineScope(Dispatchers.IO).launch {
            mealsDao.deleteMeal()
        }
    }
    fun addToFavourite(favouriteEntity: FavouriteEntity){
        CoroutineScope(Dispatchers.IO).launch {
            val favourite=FavouriteEntity(
                favouriteEntity.strMealThumb,
                favouriteEntity.idMeal,
                favouriteEntity.strMeal
            )
            mealsDao.addToFav(favourite)
        }
    }
    fun getFavourite() = mealsDao.getFav()
    fun deleteFavourite(id:String)=mealsDao.deleteFav(id)
    companion object {
        @Volatile
        private var instance: MealRepository? = null
        fun getInstance(
            apiService: ApiService,
            mealsDao: MealsDao,
            appExecutors: AppExecutors
        ): MealRepository =
            instance ?: synchronized(this) {
                instance ?: MealRepository(apiService, mealsDao, appExecutors)
            }.also { instance = it }
    }
}