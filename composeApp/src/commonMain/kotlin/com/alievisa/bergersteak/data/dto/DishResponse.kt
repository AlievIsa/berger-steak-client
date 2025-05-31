package com.alievisa.bergersteak.data.dto

import com.alievisa.bergersteak.domain.models.DishModel
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

fun DishResponse.toModel() = DishModel(
    id = id,
    categoryId = categoryId,
    name = name,
    price = price,
    description = description,
    image = image,
    weight = weight,
    calories = calories,
)