package com.alievisa.bergersteak.ui.screens.basket

import com.alievisa.bergersteak.domain.models.BasketModel

data class BasketState(
    val basketModel: BasketModel = BasketModel(positions = emptyList(), totalPrice = 0)
)