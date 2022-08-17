package com.umain.fooddelivery.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umain.fooddelivery.data.model.RestaurantResponse
import com.umain.fooddelivery.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mRepository: Repository
) : ViewModel() {

    private val _restaurantResponse = MutableSharedFlow<RestaurantResponse>(replay = 1)
    val restaurantResponse = _restaurantResponse.asSharedFlow()

    init {
        startFlow()
    }

    private fun startFlow() {
        viewModelScope.launch {
            mRepository.getRestaurants()
                .catch { e ->
                    Timber.e("${e.message}")
                }.collect { value ->
                    _restaurantResponse.emit(value)
                }
        }
    }

}