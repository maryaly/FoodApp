package com.umain.fooddelivery.ui.adapter.restaurant

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umain.fooddelivery.data.model.Filter
import com.umain.fooddelivery.data.model.Restaurant
import com.umain.fooddelivery.databinding.ItemRestaurantBinding
import com.umain.fooddelivery.utils.resource.ResourceUtilHelper

class RestaurantAdapter(
    private val listener: RestaurantClickListener,
    private val restaurantList: List<Restaurant>,
    private val filterList: HashSet<Filter>,
    private val mResourceUtilHelper: ResourceUtilHelper
) : RecyclerView.Adapter<RestaurantViewHolder>() {

    private lateinit var binding: ItemRestaurantBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        binding = ItemRestaurantBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RestaurantViewHolder(binding, mResourceUtilHelper)
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        val restaurant = restaurantList[position]
        holder.bind(
            restaurant,
            listener,
            filterList
        )
    }

    override fun getItemCount(): Int = restaurantList.size

}
