package com.example.workschedule.domain.models

/**
 * Driver entity
 *
 * @param id
 * @param name
 * @param surname
 * @param patronymic
 * @param workedTime projected work hours this month
 * @param totalTime total work hours this month
 * @param accessTrainsId admittance list
 */

data class Driver(
    val id: Int,
    val personnelNumber: Int,
    val surname: String,
    val name: String,
    val patronymic: String,
    var workedTime: Long,
    var totalTime: Long,
    var accessTrainsId: List<Int>
)
