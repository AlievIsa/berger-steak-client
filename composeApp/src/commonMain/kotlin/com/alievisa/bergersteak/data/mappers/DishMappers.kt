package com.alievisa.bergersteak.data.mappers

import com.alievisa.bergersteak.data.local.entities.DishEntity
import com.alievisa.bergersteak.data.network.dto.DishResponse
import com.alievisa.bergersteak.domain.models.DishModel

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

fun DishModel.toEntity() = DishEntity(
    id = id,
    categoryId = categoryId,
    name = name,
    price = price,
    description = description,
    image = image,
    weight = weight,
    calories = calories,
)

fun DishEntity.toModel() = DishModel(
    id = id,
    categoryId = categoryId,
    name = name,
    price = price,
    description = description,
    image = image,
    weight = weight,
    calories = calories,
)