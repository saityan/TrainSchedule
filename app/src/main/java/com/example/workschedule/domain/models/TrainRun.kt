package com.example.workschedule.domain.models

import java.time.LocalDateTime

/**
 * Route entity
 *
 * @param id
 * @param trainId
 * @param trainNumber
 * @param trainDirection
 * @param trainPeriodicity
 * @param driverId
 * @param driverName
 * @param startTime departure time
 * @param travelTime time from departure to arrival
 * @param travelRestTime rest time for a driver
 * @param backTravelTime time to travel back (rest time stays the same)
 */

data class TrainRun(
    val id: Int,
    val trainId: Int,
    val trainNumber: Int,
    val trainDirection: String,
    val trainPeriodicity: TrainPeriodicity,
    var driverId: Int,
    var driverName: String,
    val startTime: LocalDateTime,
    val travelTime: Long,
    val travelRestTime: Long,
    val backTravelTime: Long
)
