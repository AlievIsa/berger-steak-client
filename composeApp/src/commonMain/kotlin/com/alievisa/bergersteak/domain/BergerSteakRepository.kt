package com.alievisa.bergersteak.domain

import com.alievisa.bergersteak.data.local.MainDao
import com.alievisa.bergersteak.data.local.entities.PositionEntity
import com.alievisa.bergersteak.data.mappers.toEntity
import com.alievisa.bergersteak.data.mappers.toModel
import com.alievisa.bergersteak.data.network.BergerSteakServer
import com.alievisa.bergersteak.domain.models.BasketModel
import com.alievisa.bergersteak.domain.models.CategoryModel
import com.alievisa.bergersteak.domain.models.DishModel
import com.alievisa.bergersteak.domain.models.MenuModel
import com.alievisa.bergersteak.domain.models.PositionModel
import com.alievisa.bergersteak.domain.models.RestaurantModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BergerSteakRepository(
    private val server: BergerSteakServer,
    private val dao: MainDao,
) {

    suspend fun getRemoteMenu(): Result<MenuModel, DataError.Remote> {
        return server.getMenu().map { it.toModel() }
    }

    suspend fun getLocalMenu() = MenuModel(categories = dao.getMenu())

    suspend fun saveMenu(menu: MenuModel) = dao.saveMenu(menu)

    suspend fun getBasketFlow() = dao.getBasketFlow()

    suspend fun increaseDishAmountInPosition(dishModel: DishModel) {
        val position = dao.getPositionByDishId(dishModel.id)

        if (position != null) {
            val newAmount = position.dishAmount + 1
            if (newAmount <= 10) {
                val updatedPosition = position.copy(dishAmount = position.dishAmount + 1)
                dao.upsertPosition(updatedPosition)
            } else {

            }
        } else {
            val newPosition = PositionEntity(
                dishId = dishModel.id,
                dishAmount = 1
            )
            dao.upsertPosition(newPosition)
        }
    }

    suspend fun decreaseDishAmountInPosition(dishModel: DishModel) {
        val position = dao.getPositionByDishId(dishModel.id)

        if (position != null) {
            val newAmount = position.dishAmount - 1
            if (newAmount > 0) {
                val updatedPosition = position.copy(dishAmount = newAmount)
                dao.upsertPosition(updatedPosition)
            } else {
                dao.deletePositionById(position.id)
            }
        }
    }

    suspend fun setDishAmountInPosition(dishModel: DishModel, amount: Int, ) {
        if (amount <= 0) {
            dao.deletePositionByDishId(dishModel.id)
            return
        }

        val position = dao.getPositionByDishId(dishModel.id)
        if (position != null) {
            val updatedPosition = position.copy(dishAmount = amount)
            dao.upsertPosition(updatedPosition)
        } else {
            val newPosition = PositionEntity(
                dishId = dishModel.id,
                dishAmount = amount
            )
            dao.upsertPosition(newPosition)
        }
    }

    suspend fun clearBasket() {
        dao.clearBasket()
    }

    suspend fun getRestaurants(): Result<List<RestaurantModel>, DataError.Remote> {
        return server.getRestaurants().map { it.toModel() }
    }
}

suspend fun MainDao.getMenu(): List<CategoryModel> {
    val categories = getCategories()
    return categories.map { category ->
        val dishes = getDishesByCategoryId(category.id)
        CategoryModel(
            id = category.id,
            name = category.name,
            dishes = dishes.map { it.toModel() }
        )
    }
}

fun MainDao.getBasketFlow(): Flow<BasketModel> = getBasketPositions().map { positions ->
    var totalPrice = 0
    BasketModel(
        positions = positions.map { entity ->
            val dishModel = getDishById(entity.dishId).toModel()
            totalPrice += dishModel.price * entity.dishAmount
            PositionModel(
                id = entity.id,
                dishModel = dishModel,
                dishAmount = entity.dishAmount,
            )
        },
        totalPrice = totalPrice,
    )
}

suspend fun MainDao.saveMenu(menu: MenuModel) {
    insertCategories(menu.categories.map { it.toEntity() })
    val dishes = menu.categories.flatMap { category -> category.dishes }
    insertDishes(dishes.map { it.toEntity() })
}