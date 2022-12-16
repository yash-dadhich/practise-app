package com.charging.practiceapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.charging.practiceapp.dao.DetailsDao
import com.charging.practiceapp.model.Details

// Annotates class to be a Room Database with a table (entity) of the Word class
@Database(entities = [Details::class], version = 1, exportSchema = false)
public abstract class DetailsRoomDB :RoomDatabase(){
    abstract fun detailsDao(): DetailsDao

    companion object {

        private var INSTANCE: DetailsRoomDB? = null

        fun getDatabase(context: Context): DetailsRoomDB {
            if (INSTANCE==null) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DetailsRoomDB::class.java,
                    "details_database"
                ).build()
                INSTANCE = instance;
            }
            return INSTANCE as DetailsRoomDB;
        }
    }
}