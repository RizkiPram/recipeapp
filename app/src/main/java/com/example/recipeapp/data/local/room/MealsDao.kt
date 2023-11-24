package com.example.recipeapp.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.recipeapp.data.local.entity.FavouriteEntity
import com.example.recipeapp.data.local.entity.MealEntity

@Dao
interface MealsDao {
    @Query("SELECT * FROM meals")
    fun getMeals(): LiveData<List<MealEntity>>

    @Query("SELECT * FROM meals WHERE strMeal LIKE :search")
    fun search(search: String): LiveData<List<MealEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFav(favouriteEntity: FavouriteEntity)

    @Query("SELECT * FROM favourite")
    fun getFav(): LiveData<List<FavouriteEntity>>

    @Query("DELETE FROM favourite WHERE favourite.idMeal=:id")
    fun deleteFav(id:String)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMeals(meal: List<MealEntity>)

    @Query("DELETE FROM meals")
    fun deleteMeal()
}