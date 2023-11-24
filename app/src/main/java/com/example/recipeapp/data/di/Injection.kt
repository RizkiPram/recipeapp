package com.example.recipeapp.data.di

import android.content.Context
import com.example.recipeapp.data.local.room.MealsDatabase
import com.example.recipeapp.data.remote.api.ApiConfig
import com.example.recipeapp.repository.MealRepository
import com.example.recipeapp.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): MealRepository {
        val apiService = ApiConfig.getApiService()
        val database = MealsDatabase.getInstance(context)
        val dao = database.mealsDao()
        val appExecutors = AppExecutors
        return MealRepository.getInstance(apiService, dao, appExecutors)
    }
}