package com.alievisa.bergersteak.ui.screens.basket

import com.alievisa.bergersteak.domain.models.BasketModel
import com.alievisa.bergersteak.domain.models.DishModel

data class BasketState(
    val basketModel: BasketModel = BasketModel(positions = emptyList(), totalPrice = 0),
    val recommendationsState: RecommendationsState = RecommendationsState.Loading,
)

sealed interface RecommendationsState {

    data object Loading: RecommendationsState

    data object Error: RecommendationsState

    data class Content(
        val recommendations: List<DishModel> = emptyList(),
    ): RecommendationsState
}