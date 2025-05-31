package com.alievisa.bergersteak.ui.screens.basket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alievisa.bergersteak.domain.BergerSteakRepository
import com.alievisa.bergersteak.domain.models.BasketModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BasketViewModel(
    private val repository: BergerSteakRepository,
): ViewModel() {

    private val _state = MutableStateFlow(BasketState(BasketModel(positions = emptyList())))
    val state = _state.asStateFlow()

    init {
        collectBasket()
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