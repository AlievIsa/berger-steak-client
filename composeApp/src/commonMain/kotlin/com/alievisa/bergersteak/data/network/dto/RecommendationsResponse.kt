package com.alievisa.bergersteak.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class RecommendationsResponse(
    val dishes: List<DishResponse>,
)