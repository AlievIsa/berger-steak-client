package com.alievisa.bergersteak.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName

@Entity
data class DishEntity(
    @PrimaryKey
    val id: Int = 0,
    @SerialName("category_id")
    val categoryId: Int = 0,
    val name: String,
    val price: Int,
    val description: String?,
    val image: String?,
    val weight: Int?,
    val calories: Int?,
)
