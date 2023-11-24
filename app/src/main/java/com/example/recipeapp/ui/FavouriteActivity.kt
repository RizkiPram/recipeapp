package com.example.recipeapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipeapp.R
import com.example.recipeapp.adapter.FavouriteAdapter
import com.example.recipeapp.adapter.MealsAdapter
import com.example.recipeapp.data.local.entity.FavouriteEntity
import com.example.recipeapp.data.local.entity.MealEntity
import com.example.recipeapp.databinding.ActivityFavouriteBinding
import com.example.recipeapp.viewmodel.FavouriteViewModel
import com.example.recipeapp.viewmodel.MainViewModel
import com.example.recipeapp.viewmodel.ViewModelFactory

class FavouriteActivity : AppCompatActivity() {
    private lateinit var binding:ActivityFavouriteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityFavouriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.imgBack.setOnClickListener { onBackPressed() }
        setupViewModel()
    }
    private fun setupViewModel(){
        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        val viewModel: FavouriteViewModel by viewModels {
            factory
        }
        viewModel.getFavourite().observe(this){
            if (it.isNullOrEmpty()){
                binding.groupNotFound.visibility= View.VISIBLE
            }
            setMealsData(it)
        }
    }
    private fun setMealsData(data: List<FavouriteEntity>) {
        val listMeals = ArrayList<FavouriteEntity>()
        data.forEach { mealsItem ->
            listMeals.add(mealsItem)
        }
        binding.rvFavourite.apply {
            val mealsAdapter = FavouriteAdapter(listMeals)
            val layout = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            layoutManager = layout
            adapter = mealsAdapter
            mealsAdapter.setOnItemClickCallback(object : FavouriteAdapter.OnItemClickCallback {
                override fun onItemClicked(data: FavouriteEntity) {
                    val intent = Intent(this@FavouriteActivity, DetailMealActivity::class.java)
                    intent.putExtra(DetailMealActivity.MEAL_ID, data.idMeal)
                    startActivity(intent)
                }
            })
        }
    }
}