package com.alievisa.bergersteak.domain.models

data class CategoryModel(
    val id: Int = 0,
    val name: String,
    val dishes: List<DishModel>,
)
