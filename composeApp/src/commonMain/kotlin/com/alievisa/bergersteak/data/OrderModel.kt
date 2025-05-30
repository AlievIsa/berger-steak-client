package com.alievisa.bergersteak.data

import berger_steak_client.composeapp.generated.resources.Res
import berger_steak_client.composeapp.generated.resources.order_canceled
import berger_steak_client.composeapp.generated.resources.order_created
import berger_steak_client.composeapp.generated.resources.order_done
import berger_steak_client.composeapp.generated.resources.order_processing
import berger_steak_client.composeapp.generated.resources.order_received
import kotlinx.datetime.Instant
import org.jetbrains.compose.resources.StringResource

data class OrderModel(
    val id: Int = 0,
    val restaurantId: Int,
    val price: Int,
    val timestamp: Instant,
    val status: OrderStatus,
    val positions: List<PositionModel>,
)

enum class OrderStatus {
    CREATED, PROCESSING, CANCELED, DONE, RECEIVED;

    companion object {
        fun OrderStatus.getStatusStringRes(): StringResource =
            when (this) {
                CREATED -> Res.string.order_created
                PROCESSING -> Res.string.order_processing
                CANCELED -> Res.string.order_canceled
                DONE -> Res.string.order_done
                RECEIVED -> Res.string.order_received
            }
    }
}