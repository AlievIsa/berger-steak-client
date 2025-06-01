package com.alievisa.bergersteak.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class RestaurantsResponse(
    val restaurants: List<RestaurantResponse>,
)

@Serializable
data class RestaurantResponse(
    val id: Int = 0,
    val address: String,
    val location: String,
)