package com.umain.fooddelivery.ui.restaurantList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.umain.fooddelivery.R
import com.umain.fooddelivery.data.model.*
import com.umain.fooddelivery.databinding.FragmentRestaurantListBinding
import com.umain.fooddelivery.ui.adapter.filter.FilterAdapter
import com.umain.fooddelivery.ui.adapter.restaurant.ClickListener
import com.umain.fooddelivery.ui.adapter.restaurant.RestaurantAdapter
import com.umain.fooddelivery.ui.base.BaseFragment
import com.umain.fooddelivery.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class RestaurantListFragment : BaseFragment() {

    private val mainViewModel: MainViewModel by activityViewModels()
    private val mRestaurantListViewModel: RestaurantListViewModel by viewModels()
    private lateinit var mRestaurantAdapter: RestaurantAdapter
    private lateinit var mFilterAdapter: FilterAdapter
    var mFilterList = hashSetOf<Filter>()

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
        mRestaurantListViewModel.mFilter.observe(viewLifecycleOwner, Observer {
            it.data?.let { data ->
                mFilterList.add(data)
                Timber.e("$mFilterList")
                setupFilterAdapter(mFilterList)
            }

        })
    }

    private fun collectRestaurants() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.restaurantResponse.collectLatest {
                    Timber.d("restaurant collected: $it")
                    setupRestaurantAdapter(it.restaurants)
                    mRestaurantListViewModel.getAllFilters(it.restaurants)
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

    private fun setupRestaurantAdapter(list: List<Restaurant>) {
        mRestaurantAdapter = RestaurantAdapter(restaurantList = list, listener = listener)
        binding.recyclerViewRestaurantListFragment.adapter = mRestaurantAdapter
        binding.recyclerViewRestaurantListFragment.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewRestaurantListFragment.setHasFixedSize(true)
    }

    private fun setupFilterAdapter(list: HashSet<Filter>) {
        mFilterAdapter = FilterAdapter(restaurantList = list, listener = listener)
        binding.includeRestaurantListFragmentHeader.recyclerViewItemHeaderFilters.adapter =
            mFilterAdapter
        binding.includeRestaurantListFragmentHeader.recyclerViewItemHeaderFilters.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.includeRestaurantListFragmentHeader.recyclerViewItemHeaderFilters.setHasFixedSize(
            true
        )
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