package com.charging.practiceapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "details_table")
class Details(
    val title:String,
    val desc:String,
    val mobile:String,
    val languages:String
){
    @PrimaryKey(autoGenerate = true)
    var id: Int=0

}
