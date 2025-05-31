package com.alievisa.bergersteak.data.mappers

import com.alievisa.bergersteak.data.local.entities.CategoryEntity
import com.alievisa.bergersteak.data.network.dto.CategoryResponse
import com.alievisa.bergersteak.domain.models.CategoryModel

fun CategoryResponse.toModel() = CategoryModel(
    id = id,
    name = name,
    dishes = dishes.map { it.toModel() }
)

fun CategoryModel.toEntity() = CategoryEntity(
    id = id,
    name = name,
)