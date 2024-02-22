package com.puterscience.algorithmteachingapp.database.settings_db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SettingsDataObject(
    @PrimaryKey val oid: Int, // arbitrary but required
    val settings: String // Save as {1/0}_{S/P/T/M}_{Num}
)