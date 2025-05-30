package com.alievisa.bergersteak

import kotlinx.serialization.Serializable

sealed class Screen {

    @Serializable
    data object Main: Screen()

    @Serializable
    data object Basket: Screen()

    @Serializable
    data object Details: Screen()

    @Serializable
    data object Profile: Screen()

    @Serializable
    data object AboutUs: Screen()

    @Serializable
    data object Auth: Screen()
}