package com.umain.fooddelivery.ui.adapter.filter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umain.fooddelivery.data.model.Restaurant
import com.umain.fooddelivery.databinding.ItemRestaurantBinding

class FilterAdapter(
    private val restaurantList: List<Restaurant>
) : RecyclerView.Adapter<FilterViewHolder>() {

    private lateinit var binding: ItemRestaurantBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder {
        binding = ItemRestaurantBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FilterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        val restaurant = restaurantList[position]
        holder.bind(restaurant)
    }

    override fun getItemCount(): Int = restaurantList.size

}
