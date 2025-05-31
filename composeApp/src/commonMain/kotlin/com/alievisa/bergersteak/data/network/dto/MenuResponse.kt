package com.alievisa.bergersteak.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class MenuResponse(
    val menu: CategoriesResponse,
)

@Serializable
data class CategoriesResponse(
    val categories: List<CategoryResponse>,
)