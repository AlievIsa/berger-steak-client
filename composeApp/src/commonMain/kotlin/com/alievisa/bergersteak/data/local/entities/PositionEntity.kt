package com.alievisa.bergersteak.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName

@Entity
data class PositionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @SerialName("dish_id")
    val dishId: Int,
    @SerialName("dish_amount")
    val dishAmount: Int,
)
