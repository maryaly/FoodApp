package com.umain.fooddelivery.ui.restaurantList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.umain.fooddelivery.BuildConfig
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
    private val mResourceUtilHelper: ResourceUtilHelper,
    private val mRepository: Repository
) : ViewModel() {

    private var _mCollection = MutableLiveData<Result<CollectionModel>>()
    val mCollection: LiveData<Result<CollectionModel>> = _mCollection

    private var _mFilter = MutableLiveData<Result<Filter>>()
    val mFilter: LiveData<Result<Filter>> = _mFilter

    var mRestaurants = MutableLiveData<List<Restaurant>>()
    var mError = MutableLiveData<String>()

    private var mJob: Job? = null


    init {
        getCollection()
    }

    private fun getCollection() {
        if (mNetworkHelper.isNetworkConnected()) {
            _mCollection.postValue(Result.loading())
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    mRepository.getCollection(
                        BuildConfig.KEY
                    ).let { response ->
                        if (response.isSuccessful) {
                            _mCollection.postValue(Result.success(response.body()))
                            mRestaurants.value?.let { getAllFilters(it) }
                        } else {
                            response.errorBody()?.let {
                                _mCollection.postValue(
                                    Result.error(
                                        mResourceUtilHelper.getResourceString(R.string.error_general)
                                    )
                                )
                            }
                        }
                    }
                } catch (exp: Exception) {
                    Timber.e(exp)
                    _mCollection.postValue(
                        Result.error(
                            Constants.EMPTY_STRING,
                            null
                        )
                    )
                }
            }
        } else {
            _mCollection.postValue(
                Result.error(
                    mResourceUtilHelper.getResourceString(R.string.no_internet_connection),
                    null
                )
            )
        }
    }

    fun getRestaurants(item: Item) {
        val output = item.response[0].body.replace("\n ", "").replace("\\\"", "")
            .replace("{   \"restaurants\":", "")
        val final = output.substring(0, output.length - 1)

        val restaurantList = Gson().fromJson(final, Array<Restaurant>::class.java).asList()
        Timber.d("$restaurantList")
        mRestaurants.postValue(restaurantList)

    }

    private fun getAllFilters(restaurantList: List<Restaurant>) {
        for (restaurant in restaurantList) {
            for (filter in restaurant.filterIds) {
                callFilterApi(filter)
            }
        }
    }

    private fun callFilterApi(filter: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                mRepository.getFilters(
                    filter
                ).let { response ->
                    if (response.isSuccessful) {
                        _mFilter.postValue(Result.success(response.body()))
                        async {

                        }
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
}