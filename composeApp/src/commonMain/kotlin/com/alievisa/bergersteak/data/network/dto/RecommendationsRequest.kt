package com.alievisa.bergersteak.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecommendationsRequest(
    @SerialName("basket_model")
    val basketModel: BasketRequest
)

@Serializable
data class BasketRequest(
    val positions: List<PositionRequest>,
    @SerialName("total_price")
    val totalPrice: Int,
)

@Serializable
data class PositionRequest(
    val id: Int,
    @SerialName("dish_id")
    val dishId: Int,
    @SerialName("dish_amount")
    val dishAmount: Int,
)