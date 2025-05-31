package com.alievisa.bergersteak.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class CategoryResponse(
    val id: Int = 0,
    val name: String,
    val dishes: List<DishResponse>,
)