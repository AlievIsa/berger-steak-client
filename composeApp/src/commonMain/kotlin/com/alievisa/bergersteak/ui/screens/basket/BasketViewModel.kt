package com.alievisa.bergersteak.ui.screens.basket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alievisa.bergersteak.domain.BergerSteakRepository
import com.alievisa.bergersteak.domain.models.DishModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BasketViewModel(
    private val repository: BergerSteakRepository,
): ViewModel() {

    private val _state = MutableStateFlow(BasketState())
    val state = _state.asStateFlow()

    init {
        collectBasket()
    }

    fun onDoneDishClick(dishModel: DishModel, dishAmount: Int) {
        viewModelScope.launch {
            repository.setDishAmountInPosition(dishModel, dishAmount)
        }
    }

    fun onIncreaseDishAmountClick(dishModel: DishModel) {
        viewModelScope.launch {
            repository.increaseDishAmountInPosition(dishModel)
        }
    }

    fun onDecreaseDishAmountClick(dishModel: DishModel) {
        viewModelScope.launch {
            repository.decreaseDishAmountInPosition(dishModel)
        }
    }

    fun clearBasket() {
        viewModelScope.launch {
            repository.clearBasket()
        }
    }

    private fun collectBasket() {
        viewModelScope.launch {
            repository.getBasketFlow().collect { basketModel ->
                _state.update {
                    it.copy(
                        basketModel = basketModel,
                    )
                }
            }
        }
    }
}