package com.example.workschedule.utils

import com.example.workschedule.domain.models.Driver
import com.example.workschedule.domain.models.TrainRun
import com.example.workschedule.domain.restHours
import com.example.workschedule.domain.secondNightWorkBan
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

/**
 * Returns collection of drivers who can't be put on the next train
 */
fun getBusyOrRestDriversIdsOnTime(trainRunList: List<TrainRun>, time: LocalDateTime) = trainRunList
    .filter { it.driverId > 0 }
    .filter {
        time >= it.startTime && time <= it.startTime.plus(
            it.travelTime + it.travelRestTime + it.backTravelTime + restHours.hoursToMillis,
            ChronoUnit.MILLIS
        )
    }
    .map { it.driverId }

/**
 * Метод для выборки из списка выезда поездов машинистов (в определенное время) и возврат списка Id тех машинистов,
 * которые были прошлую ночь в рейсе и попадают в ночь в этом выезде. Т.е. списка тех кого нельзя ставить на новый выезд в
 * определенное время.
 */
fun getDriversIdsWhoWorkSecondNight(
    trainRunList: List<TrainRun>, currentTrainRun: TrainRun, drivers: List<Driver>
): List<Int> {
    fun periodInNight(
        searchDay: LocalDateTime, startTime: LocalDateTime, endTime: LocalDateTime
    ): Boolean {
        val nightStart = searchDay.toLocalDate().atStartOfDay()
        val nightEnd = nightStart.plusHours(6)
        return startTime <= nightEnd && endTime >= nightStart
    }

    val lastNightWorkDriversIdList = trainRunList
        .asSequence()
        .filter { it.driverId > 0 } // Отсеиваем записи в которых машинисты еще не назначены
        .filter {
            currentTrainRun.startTime.minusDays(1).toLocalDate().atStartOfDay() > it.startTime.plus(
                it.travelTime + it.travelRestTime + it.backTravelTime,
                ChronoUnit.MILLIS
            )
        }   // Отсеиваем тех у кого смена закончилась до вчерашней ночи
        .filter {
            periodInNight(
                currentTrainRun.startTime.minusDays(1),
                it.startTime,
                it.startTime.plus(
                    it.travelTime, ChronoUnit.MILLIS
                )
            ) || periodInNight(
                currentTrainRun.startTime.minusDays(1),
                it.startTime.plus(
                    it.travelTime + it.travelRestTime, ChronoUnit.MILLIS
                ),
                it.startTime.plus(
                    it.travelTime + it.travelRestTime + it.backTravelTime, ChronoUnit.MILLIS
                )
            )
        }   // Выбираем тех, кто работал в прошлую ночь
        .map { it.driverId }    // Мапим в список их Id
        .toList()
    val startTime = currentTrainRun.startTime
    val currentTrainRunInNight =
        periodInNight(
            startTime, startTime, startTime.plus(
                currentTrainRun.travelTime, ChronoUnit.MILLIS
            )
        ) || periodInNight(
            startTime, startTime.plus(
                currentTrainRun.travelTime + currentTrainRun.travelRestTime, ChronoUnit.MILLIS
            ),
            startTime.plus(
                currentTrainRun.travelTime + currentTrainRun.travelRestTime + currentTrainRun.backTravelTime,
                ChronoUnit.MILLIS
            )
        )
    return if (!currentTrainRunInNight) {
        listOf()
    } else {
        drivers.getFreeDriversForTrainRun(
            trainRunList, currentTrainRun
        ) // Берем список свободных машинистов на данное направление
            .map { it.id } // Мапим их Id в список
            .filter { it in lastNightWorkDriversIdList } // Оставляем тех, кто работал прошлой ночью
    }
}

/**
 * Returns collection of eligible drivers
 */
fun List<Driver>.getFreeDriversForTrainRun(trainRunList: List<TrainRun>, trainRun: TrainRun) =
    this
        .filter { trainRun.trainId in it.accessTrainsId }
        .filter { it.id !in getBusyOrRestDriversIdsOnTime(trainRunList, trainRun.startTime) }

/**
 * Main algorithm to assign drivers automatically
 */
fun List<TrainRun>.fillTrainRunListWithDrivers(drivers: List<Driver>): List<TrainRun> {
    this.forEach { trainRun ->  // for every train
        if (trainRun.driverId == 0) {   // if driver wasn't busy
            trainRun.driverId = drivers
                // get eligible drivers
                .getFreeDriversForTrainRun(this, trainRun)
                .filter {
                    if (secondNightWorkBan) {
                        it.id !in getDriversIdsWhoWorkSecondNight(this, trainRun, drivers)
                    } else {
                        true
                    }
                }
                /*
                 * Taking two nights rule into account,
                 * choosing the driver who has had the least working shifts this month
                 */
                .minByOrNull { it.totalTime }?.id ?: 0
            if (trainRun.driverId != 0) {   // if driver found
                drivers.find { it.id == trainRun.driverId }?.let { driver ->
                    trainRun.driverName = driver.FIO
                    // calculate required time for a shift
                    val workTime = trainRun.travelTime + trainRun.backTravelTime
                    driver.totalTime = driver.totalTime.plus(workTime)
                    val travelEndTime = trainRun.startTime.plus(
                        trainRun.travelTime + trainRun.travelRestTime + trainRun.backTravelTime,
                        ChronoUnit.MILLIS
                    )
                    if (LocalDateTime.now() > travelEndTime) {
                        driver.workedTime = driver.workedTime.plus(workTime)
                    }
                }
            } else {
                // todo return a message that there's not enough drivers to fill the shifts completely
            }
        }
    }
    return this
}
