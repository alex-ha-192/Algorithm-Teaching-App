package com.puterscience.algorithmteachingapp.database.dataset_db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Dataset(
    @PrimaryKey val oid: Int, // arbitrary but i need a PK
    @ColumnInfo(name = "Name") var name: String,
    @ColumnInfo(name = "Items") var listInts: String, // SAVE AS INT_INT_INT
    @ColumnInfo(name = "Edited") var edited: Int // i'm pretty sure this is unused
)