package com.puterscience.algorithmteachingapp.database.settings_db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.puterscience.algorithmteachingapp.database.db_classes.Dataset

@Dao
interface settingsDao {
    @Query("SELECT settings FROM SettingsDataObject")
    fun getSettings(): Array<String>

    @Update
    fun updateSettings(settings: SettingsDataObject)

    @Insert
    fun addSettings(settings: SettingsDataObject)
}