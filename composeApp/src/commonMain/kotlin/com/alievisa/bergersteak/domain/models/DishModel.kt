package com.alievisa.bergersteak.domain.models

data class DishModel(
    val id: Int = 0,
    val categoryId: Int = 0,
    val name: String,
    val price: Int,
    val description: String?,
    val image: String?,
    val weight: Int?,
    val calories: Int?,
)