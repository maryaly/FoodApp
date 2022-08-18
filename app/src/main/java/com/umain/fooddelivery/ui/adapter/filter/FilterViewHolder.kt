package com.umain.fooddelivery.ui.adapter.filter

import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.request.CachePolicy
import coil.transform.RoundedCornersTransformation
import com.umain.fooddelivery.R
import com.umain.fooddelivery.data.model.Filter
import com.umain.fooddelivery.databinding.ItemFilterBinding
import timber.log.Timber


class FilterViewHolder(
    private val binding: ItemFilterBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(filter: Filter, listener: FilterClickListener) {
        Timber.d("$filter")
        binding.textViewItemFilterTitle.text = filter.name
        binding.imageViewItemFilterFilterImage.load(
            filter.imageURL
        ) {
            memoryCachePolicy(CachePolicy.ENABLED)
            crossfade(true)
            error(R.drawable.place_holder)
            transformations(RoundedCornersTransformation(
                12f, 12f, 0f, 0f))
        }
        binding.constraintLayoutItemFilter.setOnClickListener {
            listener.OnListenerClicked(filter)
        }
    }
}