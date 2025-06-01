package com.alievisa.bergersteak.domain.models

data class BasketModel(
    val positions: List<PositionModel>,
    val totalPrice: Int,
)