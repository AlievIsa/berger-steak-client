package com.alievisa.bergersteak.data.dto

import com.alievisa.bergersteak.domain.models.MenuModel
import kotlinx.serialization.Serializable

@Serializable
data class MenuResponse(
    val menu: CategoriesResponse,
)

@Serializable
data class CategoriesResponse(
    val categories: List<CategoryResponse>,
)

fun MenuResponse.toModel() = MenuModel(
    categories = menu.categories.map { it.toModel() }
)