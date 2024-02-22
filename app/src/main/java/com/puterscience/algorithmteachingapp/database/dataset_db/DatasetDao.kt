package com.puterscience.algorithmteachingapp.database.dataset_db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface DatasetDao {
    @Query("SELECT * FROM Dataset") // get all elements from dataset
    fun getAll(): Array<Dataset>

    @Query("SELECT * FROM Dataset ORDER BY Name ASC") // order all elements by name
    fun getNameAscending(): Array<Dataset>

    @Query("SELECT * FROM Dataset ORDER BY Name DESC")
    fun getNameDescending(): Array<Dataset>

    @Query("SELECT * FROM Dataset ORDER BY Edited DESC")
    fun getMostRecent(): Array<Dataset>

    @Query("SELECT * FROM Dataset ORDER BY Edited ASC")
    fun getOldest(): Array<Dataset>

    @Query("SELECT Name FROM Dataset")
    fun getNames(): Array<String>

    @Insert // insert tag automatically generates a sql query
    fun addDataset(dataset: Dataset) // take datasets objects as a parameter

    @Delete
    fun removeDataset(dataset: Dataset)

    @Update
    fun updateDataset(dataset: Dataset)
}