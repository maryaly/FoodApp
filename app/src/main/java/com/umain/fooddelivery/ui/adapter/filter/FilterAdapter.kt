package com.umain.fooddelivery.ui.adapter.filter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umain.fooddelivery.data.model.Filter
import com.umain.fooddelivery.data.model.Restaurant
import com.umain.fooddelivery.databinding.ItemFilterBinding
import com.umain.fooddelivery.databinding.ItemRestaurantBinding
import com.umain.fooddelivery.ui.adapter.restaurant.ClickListener

class FilterAdapter(
    private val listener: ClickListener,
    private val restaurantList: HashSet<Filter>
) : RecyclerView.Adapter<FilterViewHolder>() {

    private lateinit var binding: ItemFilterBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder {
        binding = ItemFilterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FilterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        val restaurant = restaurantList.elementAt(position)
        holder.bind(restaurant)
    }

    override fun getItemCount(): Int = restaurantList.size

}
