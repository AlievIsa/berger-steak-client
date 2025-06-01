package com.alievisa.bergersteak.data.mappers

import com.alievisa.bergersteak.data.network.dto.BasketRequest
import com.alievisa.bergersteak.data.network.dto.PositionRequest
import com.alievisa.bergersteak.data.network.dto.RecommendationsRequest
import com.alievisa.bergersteak.domain.models.BasketModel
import com.alievisa.bergersteak.domain.models.PositionModel

fun BasketModel.toRecommendationsRequest() = RecommendationsRequest(
    basketModel = BasketRequest(
        positions = this.positions.map { it.toPositionRequest() },
        totalPrice = this.totalPrice,
    )
)

fun PositionModel.toPositionRequest() = PositionRequest(
    id = id,
    dishId = dishModel.id,
    dishAmount = dishAmount,
)