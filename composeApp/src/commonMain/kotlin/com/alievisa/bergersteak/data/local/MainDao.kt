package com.alievisa.bergersteak.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.alievisa.bergersteak.data.local.entities.CategoryEntity
import com.alievisa.bergersteak.data.local.entities.DishEntity
import com.alievisa.bergersteak.data.local.entities.PositionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MainDao {

    @Upsert
    suspend fun insertCategories(categories: List<CategoryEntity>)

    @Query("SELECT * FROM CategoryEntity")
    suspend fun getCategories(): List<CategoryEntity>

    @Upsert
    suspend fun insertDishes(dishes: List<DishEntity>)

    @Query("SELECT * FROM DishEntity WHERE categoryId = :categoryId")
    suspend fun getDishesByCategoryId(categoryId: Int): List<DishEntity>

    @Query("SELECT * FROM DishEntity WHERE id = :id")
    suspend fun getDishById(id: Int): DishEntity

    @Upsert
    suspend fun upsertPosition(position: PositionEntity)

    @Query("SELECT * FROM PositionEntity WHERE dishId = :dishId LIMIT 1")
    suspend fun getPositionByDishId(dishId: Int): PositionEntity?

    @Query("SELECT * FROM PositionEntity")
    fun getBasketPositions(): Flow<List<PositionEntity>>

    @Delete
    suspend fun deletePosition(position: PositionEntity)

    @Query("DELETE FROM PositionEntity")
    suspend fun clearBasket()
}