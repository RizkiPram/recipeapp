package com.example.recipeapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipeapp.data.remote.response.MealsItem
import com.example.recipeapp.databinding.ItemRecipeBinding

class MealsAdapter(private val list: ArrayList<MealsItem>) :
    RecyclerView.Adapter<MealsAdapter.ViewHolder>() {
    inner class ViewHolder(private var binding: ItemRecipeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun itemBind(data:MealsItem){
            binding.apply {
                Glide.with(itemView)
                    .load(data.strMealThumb)
                    .into(imgRecipe)
                tvRecipeCategory.text=data.strMeal
                tvRecipeName.text=data.strMeal
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemBind(list[position])
    }
}