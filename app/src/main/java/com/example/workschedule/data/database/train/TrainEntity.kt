package com.example.workschedule.data.database.train

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Table structure for the list of trains
 */

@Entity
data class TrainEntity(

    @PrimaryKey(autoGenerate = true)
    @field:ColumnInfo(name = "id")

    var id: Int,
    @field:ColumnInfo(name = "direction")
    val direction: String
)
