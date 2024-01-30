package com.puterscience.algorithmteachingapp.database.dataset_db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Dataset::class], version = 1)
abstract class DatasetDatabase : RoomDatabase() {
    abstract fun datasetDao(): DatasetDao
}
