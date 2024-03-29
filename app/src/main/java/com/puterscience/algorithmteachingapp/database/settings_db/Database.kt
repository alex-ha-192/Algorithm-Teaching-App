package com.puterscience.algorithmteachingapp.database.settings_db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [SettingsDataObject::class], version = 1) // another standard implementation
abstract class SettingsDatabase : RoomDatabase() {
    abstract fun settingsDao(): settingsDao
}
