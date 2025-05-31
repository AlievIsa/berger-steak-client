package com.alievisa.bergersteak.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DishResponse(
    val id: Int = 0,
    @SerialName("category_id")
    val categoryId: Int = 0,
    val name: String,
    val price: Int,
    val description: String?,
    val image: String?,
    val weight: Int?,
    val calories: Int?,
)