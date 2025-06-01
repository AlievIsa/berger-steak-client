package com.alievisa.bergersteak.ui.screens.basket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alievisa.bergersteak.domain.BergerSteakRepository
import com.alievisa.bergersteak.domain.models.DishModel
import com.alievisa.bergersteak.domain.onError
import com.alievisa.bergersteak.domain.onSuccess
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
            var isFirstEmission = true
            repository.getBasketFlow().collect { basketModel ->
                _state.update {
                    it.copy(
                        basketModel = basketModel,
                    )
                }

                if (isFirstEmission) {
                    isFirstEmission = false
                    loadRecommendations()
                }
            }
        }
    }

    private fun loadRecommendations() {
        viewModelScope.launch {
            repository.getRecommendations(_state.value.basketModel).onSuccess { recommendations ->
                _state.update {
                    it.copy(
                        recommendationsState = RecommendationsState.Content(recommendations),
                    )
                }
            }.onError {
                _state.update {
                    it.copy(
                        recommendationsState = RecommendationsState.Error,
                    )
                }
            }
        }
    }

    fun onAddDishClick(dishModel: DishModel, dishAmount: Int? = null) {
        viewModelScope.launch {
            if (dishAmount != null) {
                repository.setDishAmountInPosition(dishModel, dishAmount)
            } else {
                repository.increaseDishAmountInPosition(dishModel)
            }
        }
    }
}