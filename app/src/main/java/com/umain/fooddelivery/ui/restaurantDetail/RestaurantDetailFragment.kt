package com.umain.fooddelivery.ui.restaurantDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.load
import coil.request.CachePolicy
import coil.transform.RoundedCornersTransformation
import com.umain.fooddelivery.R
import com.umain.fooddelivery.databinding.FragmentRestaurantDetailBinding
import com.umain.fooddelivery.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RestaurantDetailFragment : BaseFragment() {

    private val mDetailViewModel: RestaurantDetailViewModel by viewModels()
    private val mArgs: RestaurantDetailFragmentArgs by navArgs()

    private var _binding: FragmentRestaurantDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRestaurantDetailBinding.inflate(inflater, container, false)
        binding.detailViewModel = mDetailViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun setupView() {
        mDetailViewModel.showInfo(mArgs.restaurant)
        showImages()
        setAnimation()
    }

    override fun setupUiListener() {
        /* NO-OP */
    }

    override fun setupObservers() {
        /* NO-OP */
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showImages() {
        mArgs.restaurant.image_url.let {
            if (it.isNotEmpty())
                binding.imageViewRestaurantDetailRestaurantImage.load(
                    it
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
    }

    private fun setAnimation() {

        val animationScaleUp = AnimationUtils.loadAnimation(context, R.anim.anim_scale_up)
        binding.cardViewRestaurantDetailDetail.startAnimation(animationScaleUp)

    }
}