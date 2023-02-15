package com.example.workschedule.data.database.driver

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * Database contract for drivers
 */

@Dao
interface DriverDao {

    /** Get drivers list */
    @Query("SELECT * FROM DriverEntity ORDER BY surname")
    suspend fun getAllDrivers(): List<DriverEntity>

    /** Get driver by id */
    @Query("SELECT * FROM DriverEntity WHERE id LIKE :driverId")
    suspend fun getDriverById(driverId: Int): DriverEntity

    /** Edit or add driver */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveDriver(driver: DriverEntity)

    /** Save driver's list */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveDriverList(driverList: List<DriverEntity>)

    /** Delete driver */
    @Query("DELETE FROM DriverEntity WHERE id = :driverId")
    suspend fun deleteDriverById(driverId: Int)

    /** Delete all drivers */
    @Query("DELETE FROM DriverEntity")
    suspend fun deleteAllDrivers()
}
