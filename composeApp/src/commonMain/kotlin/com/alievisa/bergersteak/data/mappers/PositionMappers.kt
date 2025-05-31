package com.alievisa.bergersteak.data.mappers

import com.alievisa.bergersteak.data.local.entities.PositionEntity
import com.alievisa.bergersteak.domain.models.BasketModel
import com.alievisa.bergersteak.domain.models.DishModel
import com.alievisa.bergersteak.domain.models.PositionModel

fun List<PositionEntity>.toBasketModel(getDishById: (Int) -> DishModel): BasketModel {
    var totalPrice = 0
    return BasketModel(
        positions = this.map { entity ->
            val dishModel = getDishById(entity.dishId)
            totalPrice += dishModel.price * entity.dishAmount
            PositionModel(
                id = entity.id,
                dishModel = dishModel,
                dishAmount = entity.dishAmount
            )
        },
        totalPrice = totalPrice,
    )
}