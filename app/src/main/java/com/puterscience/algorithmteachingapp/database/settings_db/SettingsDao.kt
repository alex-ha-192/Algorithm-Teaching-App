package com.puterscience.algorithmteachingapp.database.settings_db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface settingsDao {
    @Query("SELECT settings FROM SettingsDataObject") // data class is defined in database/settings_db/Settings.kt
    fun getSettings(): Array<String>

    @Update
    fun updateSettings(settings: SettingsDataObject)

    @Insert
    fun addSettings(settings: SettingsDataObject)
}