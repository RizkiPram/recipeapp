package com.example.recipeapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.data.remote.response.IngredientDataClass
import com.example.recipeapp.databinding.ItemIngredientsBinding

class IngredientsAdapter(private val list: ArrayList<String>) :
RecyclerView.Adapter<IngredientsAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding:ItemIngredientsBinding):RecyclerView.ViewHolder(binding.root){
        fun itemBind(data:String){
            binding.apply {
                tvNameIngridient.text=data
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientsAdapter.ViewHolder {
        val binding =
            ItemIngredientsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount()=list.size

    override fun onBindViewHolder(holder: IngredientsAdapter.ViewHolder, position: Int) {
        holder.itemBind(list[position])
    }
}