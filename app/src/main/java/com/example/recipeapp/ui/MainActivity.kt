package com.example.recipeapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipeapp.R
import com.example.recipeapp.adapter.MealsAdapter
import com.example.recipeapp.data.remote.response.MealsItem
import com.example.recipeapp.databinding.ActivityMainBinding
import com.example.recipeapp.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<MainViewModel>()
    private lateinit var binding:ActivityMainBinding
    private var listCategory = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.getCategoryRecipe()
        viewModel.listRecipe.observe(this){
            setMealsData(it)
        }
        viewModel.listCategory.observe(this){
            it.forEach {category ->
                listCategory.add(category.strCategory)
            }
            viewModel.getRecipe(listCategory[2])
        }

    }
    private fun setMealsData(data:List<MealsItem>){
        val listMeals = ArrayList<MealsItem>()
        data.forEach {
            mealsItem ->
            listMeals.add(mealsItem)
        }
        binding.rvRecipe.apply {
            adapter= MealsAdapter(listMeals)
            val layout= LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            layoutManager=layout
        }
    }
}