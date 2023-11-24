package com.example.recipeapp.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.recipeapp.data.local.entity.FavouriteEntity
import com.example.recipeapp.data.local.entity.MealEntity


@Database(entities = [MealEntity::class,FavouriteEntity::class], version = 1, exportSchema = false)
abstract class MealsDatabase:RoomDatabase() {
    abstract fun mealsDao():MealsDao

    companion object {
        @Volatile
        private var instance: MealsDatabase? = null
        fun getInstance(context: Context): MealsDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    MealsDatabase::class.java, "Meals.db"
                ).build()
            }
    }
}