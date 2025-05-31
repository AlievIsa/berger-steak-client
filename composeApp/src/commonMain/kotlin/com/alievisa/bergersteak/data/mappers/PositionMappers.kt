package com.alievisa.bergersteak.data.mappers

import com.alievisa.bergersteak.data.local.entities.PositionEntity
import com.alievisa.bergersteak.domain.models.BasketModel
import com.alievisa.bergersteak.domain.models.DishModel
import com.alievisa.bergersteak.domain.models.PositionModel

fun List<PositionEntity>.toBasketModel(getDishById: (Int) -> DishModel): BasketModel {
    return BasketModel(
        positions = this.map { entity ->
            PositionModel(
                id = entity.id,
                dishModel = getDishById(entity.dishId),
                dishAmount = entity.dishAmount
            )
        }
    )
}