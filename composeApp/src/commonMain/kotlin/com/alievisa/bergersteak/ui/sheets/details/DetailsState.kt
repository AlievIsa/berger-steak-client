package com.alievisa.bergersteak.ui.sheets.details

import com.alievisa.bergersteak.domain.models.RestaurantModel

data class DetailsState(
    val restaurants: List<RestaurantModel> = emptyList(),
    val paymentState: PaymentState = PaymentState.INITIAL,
    val selectedOrderTypeIndex: Int = 0,
    val selectedRestaurantIndex: Int = 0,
)

enum class PaymentState {
    INITIAL, LOADING, SUCCESS, ERROR
}