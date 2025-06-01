package com.alievisa.bergersteak.data.mappers

import com.alievisa.bergersteak.data.network.dto.RestaurantResponse
import com.alievisa.bergersteak.data.network.dto.RestaurantsResponse
import com.alievisa.bergersteak.domain.models.RestaurantModel

fun RestaurantsResponse.toModel() = this.restaurants.map { it.toModel() }

fun RestaurantResponse.toModel() = RestaurantModel(
    id = id,
    address = address,
    location = location,
)