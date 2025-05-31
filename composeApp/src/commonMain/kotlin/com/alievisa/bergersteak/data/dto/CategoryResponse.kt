package com.alievisa.bergersteak.data.dto

import com.alievisa.bergersteak.domain.models.CategoryModel
import kotlinx.serialization.Serializable

@Serializable
data class CategoryResponse(
    val id: Int = 0,
    val name: String,
    val dishes: List<DishResponse>,
)

fun CategoryResponse.toModel() = CategoryModel(
    id = id,
    name = name,
    dishes = dishes.map { it.toModel() }
)