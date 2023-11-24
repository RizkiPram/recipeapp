package com.example.recipeapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipeapp.R
import com.example.recipeapp.data.local.entity.FavouriteEntity
import com.example.recipeapp.databinding.ItemRecipeBinding

class FavouriteAdapter(private val list: List<FavouriteEntity>) :
    RecyclerView.Adapter<FavouriteAdapter.ViewHolder>() {
    private var onItemClickCallback: OnItemClickCallback? = null

    inner class ViewHolder(private val binding: ItemRecipeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun itemBind(data: FavouriteEntity) {
            binding.apply {
                root.setOnClickListener { onItemClickCallback?.onItemClicked(data) }
                tvRecipeName.text = data.strMeal
                tvRecipeCategory.text = data.strMeal
                Glide.with(itemView)
                    .load(data.strMealThumb)
                    .placeholder(R.drawable.img)
                    .into(imgRecipe)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemBind(list[position])
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: FavouriteEntity)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
}