package com.umain.fooddelivery.ui.adapter.restaurant

import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.request.CachePolicy
import coil.transform.RoundedCornersTransformation
import com.umain.fooddelivery.R
import com.umain.fooddelivery.data.model.Restaurant
import com.umain.fooddelivery.databinding.ItemRestaurantBinding
import com.umain.fooddelivery.utils.resource.ResourceUtilHelper
import timber.log.Timber


class RestaurantViewHolder(
    private val binding: ItemRestaurantBinding,
    private val mResourceUtilHelper: ResourceUtilHelper
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(restaurant: Restaurant, listener: ClickListener) {
        Timber.d("$restaurant")
        binding.textViewItemRestaurantScore.text = restaurant.rating.toString()
        binding.textViewItemRestaurantTitle.text = restaurant.name
        binding.textViewItemRestaurantSubTitle.text = restaurant.filterIds.toString()
        if (restaurant.delivery_time_minutes != 1 && restaurant.delivery_time_minutes != 60)
            binding.textViewItemRestaurantTime.text =
                "${restaurant.delivery_time_minutes} ${mResourceUtilHelper.getResourceString(R.string.mins)}"
        else if (restaurant.delivery_time_minutes == 60)
            binding.textViewItemRestaurantTime.text = mResourceUtilHelper.getResourceString(R.string.one_hour)
        else
            binding.textViewItemRestaurantTime.text =
                "${restaurant.delivery_time_minutes} ${mResourceUtilHelper.getResourceString(R.string.min)}"

        binding.imageViewItemRestaurantRestaurantImage.load(
            restaurant.image_url
        ) {
            memoryCachePolicy(CachePolicy.ENABLED)
            crossfade(true)
            error(R.drawable.place_holder)
            transformations(
                RoundedCornersTransformation(
                    12f, 12f, 0f, 0f
                )
            )
        }

        binding.constraintLayoutItemRestaurant.setOnClickListener {
            listener.OnListenerClicked(restaurant)
        }
    }
}