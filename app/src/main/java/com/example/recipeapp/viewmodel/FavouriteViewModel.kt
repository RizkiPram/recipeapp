package com.example.recipeapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.recipeapp.repository.MealRepository

class FavouriteViewModel(private val mealRepository: MealRepository): ViewModel() {
    fun getFavourite()=mealRepository.getFavourite()

}