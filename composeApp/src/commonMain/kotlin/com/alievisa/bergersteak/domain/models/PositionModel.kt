package com.alievisa.bergersteak.domain.models

data class PositionModel(
    val id: Int = 0,
    val dishModel: DishModel,
    val dishAmount: Int,
)
