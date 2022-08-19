package com.umain.fooddelivery.ui.adapter.filter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.request.CachePolicy
import coil.transform.RoundedCornersTransformation
import com.umain.fooddelivery.R
import com.umain.fooddelivery.data.model.Filter
import com.umain.fooddelivery.databinding.ItemFilterBinding

class FilterAdapter(
    private val listener: FilterClickListener,
    private val filterList: HashSet<Filter>
) : RecyclerView.Adapter<FilterAdapter.FilterViewHolder>() {

    private lateinit var binding: ItemFilterBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder {
        binding = ItemFilterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FilterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        val filter = filterList.elementAt(position)
        var selectedItemPosition = 0

        binding.constraintLayoutItemFilter.setOnClickListener {
            listener.OnListenerClicked(filter)
            selectedItemPosition = position
            notifyDataSetChanged()
        }
        if (selectedItemPosition == position) {
            binding.textViewItemFilterTitle.setTextColor(
                binding.textViewItemFilterTitle.context.resources.getColor(
                    R.color.white
                )
            )
            binding.constraintLayoutItemFilter.setBackgroundResource(R.drawable.bg_all_curve_selected)
        } else {
            binding.textViewItemFilterTitle.setTextColor(
                binding.textViewItemFilterTitle.context.resources.getColor(
                    R.color.black
                )
            )
            binding.constraintLayoutItemFilter.setBackgroundResource(R.drawable.bg_all_curve_unselected)
        }
        binding.textViewItemFilterTitle.text = filter.name
        binding.imageViewItemFilterFilterImage.load(
            filter.imageURL
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
    }

    override fun getItemCount(): Int = filterList.size

    class FilterViewHolder(
        private val binding: ItemFilterBinding
    ) : RecyclerView.ViewHolder(binding.root)

}
