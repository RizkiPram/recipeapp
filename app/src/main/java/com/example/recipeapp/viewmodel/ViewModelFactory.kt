package com.example.recipeapp.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recipeapp.data.di.Injection
import com.example.recipeapp.repository.MealRepository

class ViewModelFactory private constructor(private val mealRepository: MealRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(mealRepository) as T
        }
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)){
            return DetailViewModel(mealRepository) as T
        }
        if (modelClass.isAssignableFrom(FavouriteViewModel::class.java)){
            return FavouriteViewModel(mealRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }
}