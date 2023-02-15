package com.example.workschedule.domain.models

/**
 * SINGLE - one-time route
 * ON_EVEN, ON_ODD - even or odd dates respectively
 * DAILY - route is active every day of the month
 */

enum class TrainPeriodicity {
    DAILY, ON_EVEN, ON_ODD, SINGLE
}
