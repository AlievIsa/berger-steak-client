package com.alievisa.bergersteak.domain.models

data class MenuModel(
    val categories: List<CategoryModel>,
)

fun MenuModel.toDishesList() = this.categories.map { it.dishes }.flatten()