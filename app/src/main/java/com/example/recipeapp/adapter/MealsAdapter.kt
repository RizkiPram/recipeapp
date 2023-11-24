package com.example.recipeapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipeapp.data.local.entity.MealEntity
import com.example.recipeapp.data.remote.response.MealsItem
import com.example.recipeapp.databinding.ItemRecipeBinding

class MealsAdapter(private val list: ArrayList<MealEntity>) :
    RecyclerView.Adapter<MealsAdapter.ViewHolder>() {
    private var onItemClickCallback : OnItemClickCallback?=null

    inner class ViewHolder(private var binding: ItemRecipeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun itemBind(data:MealEntity){
            binding.apply {
                root.setOnClickListener {
                    onItemClickCallback?.onItemClicked(data)
                }
                Glide.with(itemView)
                    .load(data.strMeal)
                    .into(imgRecipe)
                tvRecipeCategory.text=data.strMealThumb
                tvRecipeName.text=data.strMealThumb
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
    interface OnItemClickCallback{
        fun onItemClicked(data: MealEntity)
    }
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }
}