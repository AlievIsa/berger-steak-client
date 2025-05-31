package com.alievisa.bergersteak.ui.screens.main

import com.alievisa.bergersteak.domain.models.BasketModel
import com.alievisa.bergersteak.domain.models.MenuModel

data class MainState(
    val searchQuery: String = "",
    val menuState: MenuState = MenuState.Loading,
    val userState: UserState = UserState.Loading,
    val basketModel: BasketModel = BasketModel(positions = emptyList(), totalPrice = 0),
)

sealed interface MenuState {

    data object Error: MenuState

    data object Loading: MenuState

    data class Content(
        val menuModel: MenuModel,
    ): MenuState
}

sealed interface UserState {

    data object Loading: UserState

    data class Content(
        val userModel: MenuModel?,
    ): UserState
}