package com.alievisa.bergersteak.data.mappers

import com.alievisa.bergersteak.data.network.dto.MenuResponse
import com.alievisa.bergersteak.domain.models.MenuModel

fun MenuResponse.toModel() = MenuModel(
    categories = menu.categories.map { it.toModel() }
)