package com.example.recipeapp.ui

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipeapp.R
import com.example.recipeapp.adapter.MealsAdapter
import com.example.recipeapp.data.local.entity.MealEntity
import com.example.recipeapp.databinding.ActivityMainBinding
import com.example.recipeapp.utils.Result
import com.example.recipeapp.viewmodel.MainViewModel
import com.example.recipeapp.viewmodel.ViewModelFactory
import com.google.android.material.chip.Chip

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var listCategory = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnMoveToFavorite.setOnClickListener {
            startActivity(Intent(this,FavouriteActivity::class.java))
        }
        setupViewModel()
    }

    private fun setupViewModel() {
        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        val viewModel: MainViewModel by viewModels {
            factory
        }
        viewModel.getCategoryRecipe()
        viewModel.listCategory.observe(this) {
            it.forEach { category ->
                listCategory.add(category.strCategory)
                val chip = Chip(binding.chipGroup.context)
                chip.chipBackgroundColor = applicationContext?.let {
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
                chip.chipCornerRadius = 5f
                chip.setOnClickListener {
                    viewModel.deleteMeal()
                    viewModel.getMeals(category.strCategory).observe(this@MainActivity) { result ->
                        if (result != null) {
                            when (result) {
                                is Result.Loading -> {
                                    binding.progressBar.visibility = View.VISIBLE
                                }

                                is Result.Success -> {
                                    binding.progressBar.visibility = View.GONE
                                    val mealData = result.data
                                    setMealsData(mealData)
                                }

                                is Result.Error -> {
                                    binding.progressBar.visibility = View.GONE
                                    Toast.makeText(
                                        this@MainActivity,
                                        "Oops Something Wrong",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            }
                        }
                    }
                }

            }
        }
        val search = binding.searchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        search.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                if (p0 != null) {
                    viewModel.searchMeal(p0).observe(this@MainActivity) { setMealsData(it)
                    }
                }
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
        })
    }

    private fun setMealsData(data: List<MealEntity>) {
        val listMeals = ArrayList<MealEntity>()
        data.forEach { mealsItem ->
            listMeals.add(mealsItem)
        }
        binding.rvRecipe.apply {
            val mealsAdapter = MealsAdapter(listMeals)
            val layout = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            layoutManager = layout
            adapter = mealsAdapter
            mealsAdapter.setOnItemClickCallback(object : MealsAdapter.OnItemClickCallback {
                override fun onItemClicked(data: MealEntity) {
                    val intent = Intent(this@MainActivity, DetailMealActivity::class.java)
                    intent.putExtra(DetailMealActivity.MEAL_ID, data.idMeal)
                    startActivity(intent)
                }
            })
        }
    }
}