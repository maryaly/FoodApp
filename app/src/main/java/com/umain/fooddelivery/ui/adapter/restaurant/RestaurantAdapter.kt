package com.umain.fooddelivery.ui.adapter.restaurant

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umain.fooddelivery.data.model.Restaurant
import com.umain.fooddelivery.databinding.ItemRestaurantBinding

class RestaurantAdapter(
    private val listener: ClickListener,
    private val restaurantList: List<Restaurant>
) : RecyclerView.Adapter<RestaurantViewHolder>() {

    private lateinit var binding: ItemRestaurantBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        binding = ItemRestaurantBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RestaurantViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        val restaurant = restaurantList[position]
        holder.bind(restaurant,listener)
    }

    override fun getItemCount(): Int = restaurantList.size

}
