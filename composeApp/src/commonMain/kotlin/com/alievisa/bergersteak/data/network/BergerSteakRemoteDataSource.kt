package com.alievisa.bergersteak.data.network

import com.alievisa.bergersteak.data.network.dto.MenuResponse
import com.alievisa.bergersteak.domain.DataError
import com.alievisa.bergersteak.domain.Result
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class BergerSteakRemoteDataSource(
    private val baseUrl: String,
    private val httpClient: HttpClient,
) {

    suspend fun getMenu(): Result<MenuResponse, DataError.Remote> {
        return safeCall {
            httpClient.get(
                urlString = "$baseUrl/v1/get-menu"
            )
        }
    }
}