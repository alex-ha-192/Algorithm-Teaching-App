package com.puterscience.algorithmteachingapp.database.settings_db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.puterscience.algorithmteachingapp.settings.settings_classes.Settings

@Entity
data class SettingsDataObject(
    @PrimaryKey val oid: Int,
    val settings: String // Save as {1/0}_{S/P/T/M}_{Num}
)