package com.alievisa.bergersteak.data

data class RestaurantModel(
    val id: Int = 0,
    val address: String,
    val location: String,
)

val mockRestaurants = listOf(
    RestaurantModel(
        id = 0,
        address = "улица Удальцова, дом 85Б",
        location = "101.234.243"
    ),
    RestaurantModel(
        id = 1,
        address = "Кутузовский проспект 15",
        location = "95.06.101"
    )
)

fun List<RestaurantModel>.findRestaurantById(id: Int) = this.firstOrNull { it.id == id }