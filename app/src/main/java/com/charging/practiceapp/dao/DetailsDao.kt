package com.charging.practiceapp.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.charging.practiceapp.model.Details
import kotlinx.coroutines.flow.Flow

@Dao
interface DetailsDao {

    @Query("SELECT * FROM details_table ORDER BY id ASC")
    suspend fun getAlphabetizedWords(): List<Details>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(details: Details)

    @Query("DELETE FROM details_table")
    suspend fun deleteAll()
}