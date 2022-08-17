package com.umain.fooddelivery.ui.restaurantList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.umain.fooddelivery.R
import com.umain.fooddelivery.data.model.*
import com.umain.fooddelivery.data.repository.Repository
import com.umain.fooddelivery.utils.Constants
import com.umain.fooddelivery.utils.network.NetworkHelper
import com.umain.fooddelivery.utils.resource.ResourceUtilHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class RestaurantListViewModel @Inject constructor(
    private val mNetworkHelper: NetworkHelper,
    val mResourceUtilHelper: ResourceUtilHelper,
    private val mRepository: Repository
) : ViewModel() {

    private var _mRestaurants = MutableLiveData<Result<RestaurantResponse>>()
    val mRestaurants: LiveData<Result<RestaurantResponse>> = _mRestaurants

    private var _mFilter = MutableLiveData<Result<Filter>>()
    val mFilter: LiveData<Result<Filter>> = _mFilter

    var mRestaurantOpen = MutableLiveData<RestaurantOpen>()

    var mError = MutableLiveData<String>()


    init {
        getRestaurants()
    }

     fun getRestaurants() {
        if (mNetworkHelper.isNetworkConnected()) {
            _mRestaurants.postValue(Result.loading())
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    mRepository.getRestaurants().let { response ->
                        if (response.isSuccessful) {
                            _mRestaurants.postValue(Result.success(response.body()))
                            mRestaurants.value?.data?.restaurants?.let {
                                Timber.e("getRestaurants -> $it")
                                getAllFilters(it)
                            }
                        } else {
                            response.errorBody()?.let {
                                _mRestaurants.postValue(
                                    Result.error(
                                        mResourceUtilHelper.getResourceString(R.string.error_general)
                                    )
                                )
                            }
                        }
                    }
                } catch (exp: Exception) {
                    Timber.e(exp)
                    _mRestaurants.postValue(
                        Result.error(
                            Constants.EMPTY_STRING,
                            null
                        )
                    )
                }
            }
        } else {
            _mRestaurants.postValue(
                Result.error(
                    mResourceUtilHelper.getResourceString(R.string.no_internet_connection),
                    null
                )
            )
        }
    }

    private fun getAllFilters(restaurantList: List<Restaurant>) {
        for (restaurant in restaurantList) {
            mRestaurantOpen.postValue(getRestaurantOpen(restaurant.id))
            for (filter in restaurant.filterIds) {
                getFilter(filter)
            }
        }
    }

    private fun getFilter(filterId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                mRepository.getFilters(
                    filterId
                ).let { response ->
                    if (response.isSuccessful) {
                        _mFilter.postValue(Result.success(response.body()))
                        Timber.e("Filter -> ***** ${mFilter.value}")
                    } else {
                        response.errorBody()?.let {
                            _mFilter.postValue(
                                Result.error(
                                    mResourceUtilHelper.getResourceString(R.string.error_general)
                                )
                            )
                        }
                    }
                }
            } catch (exp: Exception) {
                Timber.e(exp)
                _mFilter.postValue(
                    Result.error(
                        Constants.EMPTY_STRING,
                        null
                    )
                )
            }
        }
    }

    private fun getRestaurantOpen(restaurantId: String): RestaurantOpen? {
        var open: RestaurantOpen? = null
        if (mNetworkHelper.isNetworkConnected()) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    mRepository.restaurantOpen(
                        restaurantId
                    ).let { response ->
                        if (response.isSuccessful)
                            open = response.body()!!
                        Timber.e("open -> $open")
                    }
                } catch (exp: Exception) {
                    Timber.e(exp)

                }
            }
        }
        return open
    }

    fun checkNetworkConnection(): Boolean {
        return mNetworkHelper.isNetworkConnected()
    }
}