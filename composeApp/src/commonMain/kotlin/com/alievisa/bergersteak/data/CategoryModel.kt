package com.alievisa.bergersteak.data

data class CategoryModel(
    val id: Int = 0,
    val name: String,
    val dishes: List<DishModel>,
)
