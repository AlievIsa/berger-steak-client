package com.alievisa.bergersteak.ui.sheets.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alievisa.bergersteak.domain.BergerSteakRepository
import com.alievisa.bergersteak.domain.models.BasketModel
import com.alievisa.bergersteak.domain.onError
import com.alievisa.bergersteak.domain.onSuccess
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val repository: BergerSteakRepository,
): ViewModel() {

    private val _state = MutableStateFlow(DetailsState())
    val state = _state.asStateFlow()

    init {
        loadRestaurants()
    }

    private fun loadRestaurants() {
        viewModelScope.launch {
            repository.getRestaurants().onSuccess { restaurants ->
                _state.update {
                    it.copy(
                        restaurants = restaurants
                    )
                }
            }.onError {  }
        }
    }

    fun onPayButtonClick(basketModel: BasketModel, callback: () -> Unit) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    paymentState = PaymentState.LOADING,
                )
            }
            delay(2000)
            _state.update {
                it.copy(
                    paymentState = PaymentState.SUCCESS,
                )
            }
            callback()
        }

    }

    fun onOrderTypeSelected(typeIndex: Int) {
        _state.update {
            it.copy(
                selectedOrderTypeIndex = typeIndex,
            )
        }
    }

    fun onRestaurantSelected(selectedIndex: Int) {
        _state.update {
            it.copy(
                selectedRestaurantIndex = selectedIndex,
            )
        }
    }
}