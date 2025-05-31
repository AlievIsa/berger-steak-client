package com.alievisa.bergersteak.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CategoryEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
)