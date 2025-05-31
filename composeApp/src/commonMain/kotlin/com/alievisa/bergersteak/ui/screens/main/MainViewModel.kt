package com.alievisa.bergersteak.ui.screens.main

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

class MainViewModel(
    private val repository: BergerSteakRepository,
): ViewModel() {

    private val _state = MutableStateFlow(MainState())
    val state = _state.asStateFlow()

    init {
        loadMenu()
        collectBasket()
    }

    private fun loadMenu() {
        viewModelScope.launch {
            repository.getRemoteMenu().onSuccess { remoteMenu ->
                _state.update {
                    it.copy(
                        menuState = MenuState.Content(menuModel = remoteMenu),
                    )
                }
                repository.saveMenu(remoteMenu)
            }.onError {
                val localMenu = repository.getLocalMenu()
                if (localMenu.categories.isNotEmpty()) {
                    _state.update {
                        it.copy(
                            menuState = MenuState.Content(menuModel = localMenu),
                        )
                    }
                } else {
                    _state.update {
                        it.copy(
                            menuState = MenuState.Error,
                        )
                    }
                }
            }
        }
    }

    fun onSearchQueryChanged(query: String) {
        _state.update {
            it.copy(
                searchQuery = query,
            )
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