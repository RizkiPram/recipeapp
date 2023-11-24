package com.example.recipeapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "favourite")
class FavouriteEntity(
    @field:SerializedName("strMealThumb")
    val strMealThumb: String?,

    @field:SerializedName("idMeal")
    @PrimaryKey
    val idMeal: String,

    @field:SerializedName("strMeal")
    val strMeal: String?
)