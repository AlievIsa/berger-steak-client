package com.alievisa.bergersteak.domain

import com.alievisa.bergersteak.data.local.MainDao
import com.alievisa.bergersteak.data.mappers.toEntity
import com.alievisa.bergersteak.data.mappers.toModel
import com.alievisa.bergersteak.data.network.BergerSteakRemoteDataSource
import com.alievisa.bergersteak.domain.models.BasketModel
import com.alievisa.bergersteak.domain.models.CategoryModel
import com.alievisa.bergersteak.domain.models.MenuModel
import com.alievisa.bergersteak.domain.models.PositionModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BergerSteakRepository(
    private val remoteDataSource: BergerSteakRemoteDataSource,
    private val dao: MainDao,
) {

    suspend fun getRemoteMenu(): Result<MenuModel, DataError.Remote> {
        return remoteDataSource.getMenu().map { it.toModel() }
    }

    suspend fun getLocalMenu() = MenuModel(categories = dao.getMenu())

    suspend fun saveMenu(menu: MenuModel) = dao.saveMenu(menu)

    suspend fun getBasketFlow() = dao.getBasketFlow()

    //suspend fun addDishToBasket() = dao.
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
    BasketModel(
        positions = positions.map { entity ->
            PositionModel(
                id = entity.id,
                dishModel = getDishById(entity.dishId).toModel(),
                dishAmount = entity.dishAmount
            )
        }
    )
}

suspend fun MainDao.saveMenu(menu: MenuModel) {
    insertCategories(menu.categories.map { it.toEntity() })
    val dishes = menu.categories.flatMap { category -> category.dishes }
    insertDishes(dishes.map { it.toEntity() })
}