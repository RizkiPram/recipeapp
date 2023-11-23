package com.example.recipeapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipeapp.R
import com.example.recipeapp.adapter.MealsAdapter
import com.example.recipeapp.data.remote.response.MealsItem
import com.example.recipeapp.databinding.ActivityMainBinding
import com.example.recipeapp.viewmodel.MainViewModel
import com.google.android.material.chip.Chip

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
                val chip= Chip(binding.chipGroup.context)
                chip.chipBackgroundColor=applicationContext?.let {
                    AppCompatResources.getColorStateList(
                        it,
                        R.color.bg_chip_selector
                    )
                }
                chip.setTextColor(applicationContext?.let {
                    AppCompatResources.getColorStateList(
                        it,
                        R.color.text_chip_selector
                    )
                })
                chip.isCheckedIconVisible = false
                chip.isCheckable = true
                chip.text = category.strCategory
                binding.chipGroup.addView(chip)
                chip.chipCornerRadius=5f
                chip.setOnClickListener {
                    viewModel.getRecipe(category.strCategory)
                }
            }

        }

    }
    private fun setMealsData(data:List<MealsItem>){
        val listMeals = ArrayList<MealsItem>()
        data.forEach {
            mealsItem ->
            listMeals.add(mealsItem)
        }
        binding.rvRecipe.apply {
            val mealsAdapter= MealsAdapter(listMeals)
            val layout= LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            layoutManager=layout
            adapter=mealsAdapter
            mealsAdapter.setOnItemClickCallback(object : MealsAdapter.OnItemClickCallback{
                override fun onItemClicked(data: MealsItem) {
                    val intent=Intent(this@MainActivity,DetailMealActivity::class.java)
                    intent.putExtra(DetailMealActivity.MEAL_ID,data.idMeal)
                    startActivity(intent)
                }
            })
        }
    }
}