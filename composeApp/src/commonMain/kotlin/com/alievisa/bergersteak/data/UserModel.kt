package com.alievisa.bergersteak.data

import kotlinx.datetime.Instant


data class UserModel(
    val id: Int = 0,
    val name: String,
    val mail: String,
    val phoneNumber: String,
    val orders: List<OrderModel>,
)

object MockUser {

    val data = UserModel(
        name = "Иса Алиев",
        mail = "aliev_iss@mail.ru",
        phoneNumber = "89257302075",
        orders = listOf(
            OrderModel(
                restaurantId = 0,
                price = 500,
                timestamp = Instant.parse("2025-02-05T14:20:00Z"),
                status = OrderStatus.CREATED,
                positions = listOf(
                    PositionModel(
                        dishId = 0,
                        dishAmount = 1,
                    )
                ),
            ),
            OrderModel(
                restaurantId = 1,
                price = 850,
                timestamp = Instant.parse("2025-02-15T19:35:00Z"),
                status = OrderStatus.PROCESSING,
                positions = listOf(
                    PositionModel(
                        dishId = 0,
                        dishAmount = 1,
                    ),
                    PositionModel(
                        dishId = 1,
                        dishAmount = 1,
                    )
                ),
            ),
            OrderModel(
                restaurantId = 1,
                price = 850,
                timestamp = Instant.parse("2025-02-15T19:35:00Z"),
                status = OrderStatus.DONE,
                positions = listOf(
                    PositionModel(
                        dishId = 0,
                        dishAmount = 1,
                    ),
                    PositionModel(
                        dishId = 1,
                        dishAmount = 1,
                    )
                ),
            ),
            OrderModel(
                restaurantId = 1,
                price = 850,
                timestamp = Instant.parse("2025-02-15T19:35:00Z"),
                status = OrderStatus.CANCELED,
                positions = listOf(
                    PositionModel(
                        dishId = 0,
                        dishAmount = 1,
                    ),
                    PositionModel(
                        dishId = 1,
                        dishAmount = 1,
                    )
                ),
            ),
        )
    )
    var isAuthorized = true
}
