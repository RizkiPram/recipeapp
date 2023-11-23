package com.example.recipeapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recipeapp.data.remote.api.ApiConfig
import com.example.recipeapp.data.remote.response.DetailResponse
import com.example.recipeapp.data.remote.response.DetailsItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {
    private val _mealDetail = MutableLiveData<List<DetailsItem>>()
    val mealDetail: MutableLiveData<List<DetailsItem>> = _mealDetail

    fun getDetail(id:String?){
        val client=ApiConfig.getApiService().getDetailMeals(id)
        client.enqueue(object : Callback<DetailResponse>{
            override fun onResponse(
                call: Call<DetailResponse>,
                response: Response<DetailResponse>
            ) {
                val responseBody=response.body()
                if (response.isSuccessful){
                    if (responseBody!=null){
                        _mealDetail.value=responseBody.meals
                    }
                }else{
                    Log.d("detail","OnFail${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                Log.d("detail","OnFailure ${t.message}")
            }
        })
    }
}