package com.alievisa.bergersteak.domain

import com.alievisa.bergersteak.data.dto.toModel
import com.alievisa.bergersteak.data.network.BergerSteakRemoteDataSource
import com.alievisa.bergersteak.domain.models.MenuModel

class BergerSteakRepository(
    private val dataSource: BergerSteakRemoteDataSource,
) {

    suspend fun getMenu(): Result<MenuModel, DataError.Remote> {
        return dataSource.getMenu().map { it.toModel() }
    }
}