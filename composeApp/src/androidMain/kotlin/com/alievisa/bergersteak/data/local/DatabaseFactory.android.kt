package com.alievisa.bergersteak.data.local

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

actual class DatabaseFactory(
    private val context: Context,
) {

    actual fun create(): RoomDatabase.Builder<BergerSteakDatabase> {
        val appContext = context.applicationContext
        val dbFile = appContext.getDatabasePath(BergerSteakDatabase.DB_NAME)

        return Room.databaseBuilder<BergerSteakDatabase>(context = appContext, name = dbFile.absolutePath)
    }
}