package com.alievisa.bergersteak.ui

import kotlinx.serialization.Serializable

sealed interface Screen {

    @Serializable
    data object Main: Screen

    @Serializable
    data object Basket: Screen

    @Serializable
    data object Profile: Screen

}