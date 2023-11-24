package com.example.recipeapp.ui

import android.os.Bundle
import android.util.Log
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.recipeapp.R
import com.example.recipeapp.adapter.IngredientsAdapter
import com.example.recipeapp.data.local.entity.FavouriteEntity
import com.example.recipeapp.data.remote.response.DetailsItem
import com.example.recipeapp.databinding.ActivityDetailMealBinding
import com.example.recipeapp.viewmodel.DetailViewModel
import com.example.recipeapp.viewmodel.ViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class DetailMealActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailMealBinding
    private lateinit var dataItem: DetailsItem
    private var ingridientsItem = ArrayList<String>()
    private var mealId=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMealBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mealId = intent.getStringExtra(MEAL_ID).toString()
        setupViewModel()

        binding.apply {
            imgBack.setOnClickListener { onBackPressed() }
            webView.settings.javaScriptEnabled = true
            webView.settings.loadWithOverviewMode=true
            webView.settings.pluginState = WebSettings.PluginState.ON
            webView.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                    return false
                }
            }
        }
    }
    private fun setupViewModel(){
        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        val viewModel: DetailViewModel by viewModels {
            factory
        }
        viewModel.getDetail(mealId)
        binding.apply {
            viewModel.mealDetail.observe(this@DetailMealActivity) {
                it.forEach { mealData ->
                    dataItem = DetailsItem(
                        mealData.strImageSource,
                        mealData.strIngredient10,
                        mealData.strIngredient12,
                        mealData.strIngredient11,
                        mealData.strIngredient14,
                        mealData.strCategory,
                        mealData.strIngredient13,
                        mealData.strIngredient16,
                        mealData.strIngredient15,
                        mealData.strIngredient18,
                        mealData.strIngredient17,
                        mealData.strArea,
                        mealData.strCreativeCommonsConfirmed,
                        mealData.strIngredient19,
                        mealData.strTags,
                        mealData.idMeal,
                        mealData.strInstructions,
                        mealData.strIngredient1,
                        mealData.strIngredient3,
                        mealData.strIngredient2,
                        mealData.strIngredient20,
                        mealData.strIngredient5,
                        mealData.strIngredient4,
                        mealData.strIngredient7,
                        mealData.strIngredient6,
                        mealData.strIngredient9,
                        mealData.strIngredient8,
                        mealData.strMealThumb,
                        mealData.strMeasure20,
                        mealData.strYoutube,
                        mealData.strMeal,
                        mealData.strMeasure12,
                        mealData.strMeasure13,
                        mealData.strMeasure10,
                        mealData.strMeasure11,
                        mealData.dateModified,
                        mealData.strDrinkAlternate,
                        mealData.strSource,
                        mealData.strMeasure9,
                        mealData.strMeasure7,
                        mealData.strMeasure8,
                        mealData.strMeasure5,
                        mealData.strMeasure6,
                        mealData.strMeasure3,
                        mealData.strMeasure4,
                        mealData.strMeasure1,
                        mealData.strMeasure18,
                        mealData.strMeasure2,
                        mealData.strMeasure19,
                        mealData.strMeasure16,
                        mealData.strMeasure17,
                        mealData.strMeasure14,
                        mealData.strMeasure15,
                        false
                    )
                }

                ingridientsItem.add(dataItem.strIngredient1 + dataItem.strMeasure1)
                ingridientsItem.add(dataItem.strIngredient2 + dataItem.strMeasure2)
                ingridientsItem.add(dataItem.strIngredient3 + dataItem.strMeasure3)
                ingridientsItem.add(dataItem.strIngredient4 + dataItem.strMeasure4)
                ingridientsItem.add(dataItem.strIngredient5 + dataItem.strMeasure5)
                ingridientsItem.add(dataItem.strIngredient6 + dataItem.strMeasure6)
                ingridientsItem.add(dataItem.strIngredient7 + dataItem.strMeasure7)
                ingridientsItem.add(dataItem.strIngredient8 + dataItem.strMeasure8)
                ingridientsItem.add(dataItem.strIngredient9 + dataItem.strMeasure9)
                ingridientsItem.add(dataItem.strIngredient10 + dataItem.strMeasure10)
                ingridientsItem.add(dataItem.strIngredient11 + dataItem.strMeasure11)
                ingridientsItem.add(dataItem.strIngredient12 + dataItem.strMeasure12)
                ingridientsItem.add(dataItem.strIngredient13 + dataItem.strMeasure13)
                ingridientsItem.add(dataItem.strIngredient14 + dataItem.strMeasure14)
                ingridientsItem.add(dataItem.strIngredient15 + dataItem.strMeasure15)
                ingridientsItem.add(dataItem.strIngredient16 + dataItem.strMeasure16)
                ingridientsItem.add(dataItem.strIngredient17 + dataItem.strMeasure17)
                ingridientsItem.add(dataItem.strIngredient18 + dataItem.strMeasure18)
                ingridientsItem.add(dataItem.strIngredient19 + dataItem.strMeasure19)
                ingridientsItem.add(dataItem.strIngredient20 + dataItem.strMeasure20)
                Glide.with(this@DetailMealActivity).load(dataItem.strMealThumb).into(imgMeal)
                tvMealDesc.text = dataItem.strInstructions
                tvMealName.text = dataItem.strMeal
                tvArea.text = dataItem.strArea
                tvCategory.text = dataItem.strCategory
                tvTags.text = dataItem.strTags
                tvAppBar.text=dataItem.strMeal
                val linkYoutube="<iframe height='95%' width='100%' src='\" + ${dataItem.strYoutube} + \"' frameborder='0' allowfullscreen></iframe>"
                    webView.loadDataWithBaseURL(
                        "https://www.youtube.com",
                        linkYoutube,
                        "text/html",
                        "UTF-8",
                        null
                    )

                setIngridientsData(ingridientsItem)
                val favouriteEntity=FavouriteEntity(
                    dataItem.strMealThumb,dataItem.idMeal,dataItem.strMeal
                )
                if (dataItem.isFavourited){
                    Glide.with(this@DetailMealActivity)
                        .load(R.drawable.baseline_favorite_24)
                        .into(imgAddToFavourite)
                }else{
                    Glide.with(this@DetailMealActivity)
                        .load(R.drawable.baseline_favorite_border_24)
                        .into(imgAddToFavourite)
                }
                imgAddToFavourite.setOnClickListener {
                    CoroutineScope(Dispatchers.IO).launch {
                        if (dataItem.isFavourited){
                            viewModel.deleteFavourite(dataItem.idMeal)
                        }else{
                            viewModel.addFavourite(favouriteEntity)
                        }
                    }
                    dataItem.isFavourited = !dataItem.isFavourited
                    if (dataItem.isFavourited){
                        Glide.with(this@DetailMealActivity)
                            .load(R.drawable.baseline_favorite_border_24)
                            .into(imgAddToFavourite)
                    }else{
                        Glide.with(this@DetailMealActivity)
                            .load(R.drawable.baseline_favorite_24)
                            .into(imgAddToFavourite)
                    }
                    Log.d("isFavourite",dataItem.isFavourited.toString())
                }
            }
        }
    }
    private fun setIngridientsData(data: ArrayList<String>) {
        binding.rvIngredients.apply {
            adapter = IngredientsAdapter(data)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }

    companion object {
        const val MEAL_ID = "meal_id"
    }
}