package com.alievisa.bergersteak.data.local

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import com.alievisa.bergersteak.data.local.entities.CategoryEntity
import com.alievisa.bergersteak.data.local.entities.DishEntity
import com.alievisa.bergersteak.data.local.entities.PositionEntity

@Database(
    entities = [DishEntity::class, CategoryEntity::class, PositionEntity::class],
    version = 1,
)
@ConstructedBy(DatabaseConstructor::class)
abstract class BergerSteakDatabase: RoomDatabase() {

    abstract val mainDao: MainDao

    companion object {

        const val DB_NAME = "BERGER_STEAK.DB"
    }
}