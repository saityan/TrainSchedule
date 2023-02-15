package com.example.workschedule.data.database.train

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * Database contract for trains
 */

@Dao
interface TrainDao {

    /** Get trains list */
    @Query("SELECT * FROM TrainEntity ORDER BY direction")
    suspend fun getAllTrains(): List<TrainEntity>

    /** Get train by number */
    @Query("SELECT * FROM TrainEntity WHERE id LIKE :trainId")
    suspend fun getTrainById(trainId: Int): TrainEntity

    /** Edit or add a train */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTrain(train: TrainEntity)

    /** Delete train */
    @Query("DELETE FROM TrainEntity WHERE id = :trainId")
    suspend fun deleteTrainById(trainId: Int)

    //Delete all trains
    @Query("DELETE FROM TrainEntity")
    suspend fun deleteAllTrains()
}
