@file:OptIn(ExperimentalForeignApi::class)

package com.alievisa.bergersteak.data.local

import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

actual class DatabaseFactory {

    actual fun create(): RoomDatabase.Builder<BergerSteakDatabase> {
        val dbFile = documentDirectory() + "/${BergerSteakDatabase.DB_NAME}"
        return Room.databaseBuilder<BergerSteakDatabase>(name = dbFile)
    }

    private fun documentDirectory(): String {
        val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null,
        )
        return requireNotNull(documentDirectory?.path)
    }
}