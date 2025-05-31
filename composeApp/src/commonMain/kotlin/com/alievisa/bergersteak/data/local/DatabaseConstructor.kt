package com.alievisa.bergersteak.data.local

import androidx.room.RoomDatabaseConstructor

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object DatabaseConstructor: RoomDatabaseConstructor<BergerSteakDatabase> {

    override fun initialize(): BergerSteakDatabase
}