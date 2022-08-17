package com.umain.fooddelivery.ui.restaurantList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.umain.fooddelivery.R
import com.umain.fooddelivery.data.model.Restaurant
import com.umain.fooddelivery.data.model.RestaurantResponse
import com.umain.fooddelivery.data.model.RestaurantOpen
import com.umain.fooddelivery.data.model.Result
import com.umain.fooddelivery.databinding.FragmentRestaurantListBinding
import com.umain.fooddelivery.ui.adapter.restaurant.ClickListener
import com.umain.fooddelivery.ui.adapter.restaurant.RestaurantAdapter
import com.umain.fooddelivery.ui.base.BaseFragment
import com.umain.fooddelivery.ui.main.MainViewModel
import com.umain.fooddelivery.utils.extentions.hideProgress
import com.umain.fooddelivery.utils.extentions.showProgress
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class RestaurantListFragment : BaseFragment() {

    private val mainViewModel: MainViewModel by activityViewModels()
    private val mRestaurantListViewModel: RestaurantListViewModel by viewModels()
    private lateinit var mAdapter: RestaurantAdapter

    private var _binding: FragmentRestaurantListBinding? = null
    private val binding get() = _binding!!

    private val listener = object : ClickListener {
        override fun OnListenerClicked(restaurant: Restaurant) {
            mRestaurantListViewModel.mRestaurantOpen.value?.let {
                navigateToDetailFragment(restaurant, it)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRestaurantListBinding.inflate(inflater, container, false)
        binding.restaurantListViewModel = mRestaurantListViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun setupView() {
        collectRestaurants()
    }

    override fun setupUiListener() {
        binding.swipeRefreshLayoutRestaurantListFragment.setOnRefreshListener {
            handleRecentRestaurantsCall()
            collectRestaurants()
        }
    }

    override fun setupObservers() {

        mRestaurantListViewModel.mRestaurants.observe(viewLifecycleOwner) {
            when (it.status) {
                Result.Status.LOADING -> showProgress()
                Result.Status.ERROR -> {
                    mRestaurantListViewModel.mError.value = it.message
                    hideProgress()
                }
                Result.Status.SUCCESS -> {
                    hideProgress()
                    it.data?.let { data ->
                        setupAdapter(data.restaurants)
                    }
                }
            }
        }

        mRestaurantListViewModel.mFilter.observe(viewLifecycleOwner, Observer {
            Timber.d("Filter -> $it")
        })
    }

    private fun collectRestaurants() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.restaurantResponse.collectLatest {
                    Timber.d("restaurant collected: $it")
                    setupAdapter(it.restaurants)
                }
            }
        }
    }

    private fun handleRecentRestaurantsCall() {
        binding.swipeRefreshLayoutRestaurantListFragment.isRefreshing = false
        if (mRestaurantListViewModel.checkNetworkConnection()) {
            binding.textViewRestaurantListFragmentError.visibility = View.INVISIBLE
            binding.recyclerViewRestaurantListFragment.visibility = View.VISIBLE
            collectRestaurants()
        } else {
            binding.recyclerViewRestaurantListFragment.visibility = View.INVISIBLE
            binding.textViewRestaurantListFragmentError.visibility = View.VISIBLE
            mRestaurantListViewModel.mError.value =
                mRestaurantListViewModel.mResourceUtilHelper.getResourceString(R.string.no_internet_connection)
        }
    }

    private fun setupAdapter(list: List<Restaurant>) {
        mAdapter = RestaurantAdapter(restaurantList = list, listener = listener)
        binding.recyclerViewRestaurantListFragment.adapter = mAdapter
        binding.recyclerViewRestaurantListFragment.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewRestaurantListFragment.setHasFixedSize(true)
    }

    private fun navigateToDetailFragment(restaurant: Restaurant, restaurantOpen: RestaurantOpen) {
        val action =
            RestaurantListFragmentDirections.actionHomeFragmentToDetailFragment(
                restaurant = restaurant,
                restaurantOpen = restaurantOpen
            )
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}