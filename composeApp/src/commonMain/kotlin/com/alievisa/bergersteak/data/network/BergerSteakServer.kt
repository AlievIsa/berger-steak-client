package com.alievisa.bergersteak.data.network

import com.alievisa.bergersteak.data.network.dto.MenuResponse
import com.alievisa.bergersteak.data.network.dto.RecommendationsRequest
import com.alievisa.bergersteak.data.network.dto.RecommendationsResponse
import com.alievisa.bergersteak.data.network.dto.RestaurantsResponse
import com.alievisa.bergersteak.domain.DataError
import com.alievisa.bergersteak.domain.Result
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody

class BergerSteakServer(
    private val baseUrl: String,
    private val httpClient: HttpClient,
) {

    suspend fun getMenu(): Result<MenuResponse, DataError.Remote> {
        return safeCall {
            httpClient.get(
                urlString = "$baseUrl/v1/get-menu",
            )
        }
    }

    suspend fun getRestaurants(): Result<RestaurantsResponse, DataError.Remote> {
        return safeCall {
            httpClient.get(
                urlString = "$baseUrl/v1/get-restaurants",
            )
        }
    }

    suspend fun getRecommendations(request: RecommendationsRequest): Result<RecommendationsResponse, DataError.Remote> {
        return safeCall {
            httpClient.post(
                urlString = "$baseUrl/v1/get-recommendations",
            ) {
                setBody(request)
            }
        }
    }
}