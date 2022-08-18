package com.umain.fooddelivery.ui.adapter.filter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umain.fooddelivery.data.model.Filter
import com.umain.fooddelivery.databinding.ItemFilterBinding

class FilterAdapter(
    private val listener: FilterClickListener,
    private val restaurantList: HashSet<Filter>
) : RecyclerView.Adapter<FilterViewHolder>() {

    private lateinit var binding: ItemFilterBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder {
        binding = ItemFilterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FilterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        val restaurant = restaurantList.elementAt(position)
        holder.bind(restaurant,listener)
    }

    override fun getItemCount(): Int = restaurantList.size

}
