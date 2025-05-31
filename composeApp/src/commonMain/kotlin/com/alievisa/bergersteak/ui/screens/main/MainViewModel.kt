package com.alievisa.bergersteak.ui.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alievisa.bergersteak.domain.BergerSteakRepository
import com.alievisa.bergersteak.domain.onError
import com.alievisa.bergersteak.domain.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: BergerSteakRepository
): ViewModel() {

    private val _state = MutableStateFlow(MainScreenState())
    val state = _state.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            repository.getMenu().onSuccess { result ->
                _state.update {
                    it.copy(
                        menuState = MenuState.Content(menuModel = result)
                    )
                }
            }.onError {
                _state.update {
                    it.copy(
                        menuState = MenuState.Error
                    )
                }
            }
        }
    }

    fun onSearchQueryChanged(query: String) {
        _state.update {
            it.copy(
                searchQuery = query
            )
        }
    }
}