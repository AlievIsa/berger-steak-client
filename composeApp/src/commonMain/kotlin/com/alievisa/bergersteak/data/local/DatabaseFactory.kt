package com.alievisa.bergersteak.data.local

import androidx.room.RoomDatabase

expect class DatabaseFactory {

    fun create(): RoomDatabase.Builder<BergerSteakDatabase>
}