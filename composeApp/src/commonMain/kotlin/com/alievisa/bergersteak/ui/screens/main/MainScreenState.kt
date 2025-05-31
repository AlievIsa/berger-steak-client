package com.alievisa.bergersteak.ui.screens.main

import com.alievisa.bergersteak.domain.models.MenuModel

data class MainScreenState(
    val searchQuery: String = "",
    val mainButtonState: MainButtonState = MainButtonState(),
    val menuState: MenuState = MenuState.Loading,
    val userState: UserState = UserState.Loading,
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

data class MainButtonState(
    val isVisible: Boolean = false,
    val amount: Int = 0,
)