package com.puterscience.algorithmteachingapp.database.dataset_db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface DatasetDao {
    @Query("SELECT * FROM Dataset")
    fun getAll(): Array<Dataset>

    @Query("SELECT * FROM Dataset ORDER BY Name ASC")
    fun getNameAscending(): Array<Dataset>

    @Query("SELECT * FROM Dataset ORDER BY Name DESC")
    fun getNameDescending(): Array<Dataset>

    @Query("SELECT * FROM Dataset ORDER BY Edited DESC")
    fun getMostRecent(): Array<Dataset>

    @Query("SELECT * FROM Dataset ORDER BY Edited ASC")
    fun getOldest(): Array<Dataset>

    @Query("SELECT Name FROM Dataset")
    fun getNames(): Array<String>

    @Insert
    fun addDataset(dataset: Dataset)

    @Delete
    fun removeDataset(dataset: Dataset)

    @Update
    fun updateDataset(dataset: Dataset)
}