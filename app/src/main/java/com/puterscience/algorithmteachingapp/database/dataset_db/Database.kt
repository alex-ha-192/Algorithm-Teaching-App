package com.puterscience.algorithmteachingapp.database.dataset_db

import androidx.room.Database
import androidx.room.RoomDatabase

// This is a pretty standard implementation from the docs
@Database(entities = [Dataset::class], version = 1)
abstract class DatasetDatabase : RoomDatabase() {
    abstract fun datasetDao(): DatasetDao // need a unique name just for dataset dao
}
