package com.example.data.common

import java.text.SimpleDateFormat
import java.util.*

const val REQUEST_FORMAT_DATE = "EEE MMM dd HH:mm:ss zzz yyyy"
const val DATE_FORMAT_MONTH_YEAR = "MM/yyyy"
const val DATE_FORMAT_MONTH_DAY = "MMM dd"
const val DATE_FORMAT_YEAR = "yyyy"
const val DATE_FORMAT_MONTH = "MM"
const val DATE_FORMAT_DAYS = "dd"
const val DATE_FORMAT_HOURS = "HH"
const val MILLISECONDS_IN_MINUTE = 60000
const val MINUTES_IN_HOUR = 60

/**
 * The method returns time or date since the twit was published
 */
fun getPublishedTwitTime(dateString: String): String {

    val publishedTwitDate = convertDate(DATE_FORMAT_MONTH_YEAR, dateString)

    val currentDayOfMonth = getCurrentDateOrTime(DATE_FORMAT_DAYS).toInt()
    val publishedTwitDays = convertDate(DATE_FORMAT_DAYS, dateString).toInt()
    val passedDaysFromPublishedTwit = currentDayOfMonth - publishedTwitDays

    val currentHours = getCurrentDateOrTime(DATE_FORMAT_HOURS).toInt()
    val publishedTwitHours = convertDate(DATE_FORMAT_HOURS, dateString).toInt()
    val passedHoursFromPublishedTwit = currentHours - publishedTwitHours

    val passedMinutesFromPublishedTwit = getPassedMinutesFromPublishedTwit(dateString)

    return when {
        isCurrentMonthOrYearGreater(publishedTwitDate) -> {
            convertDate(DATE_FORMAT_MONTH_DAY, dateString)
        }
        isCurrentDayOfMonthGreaterThenPublishedTwitDay(passedDaysFromPublishedTwit) -> {
            "${passedDaysFromPublishedTwit}d"
        }
        isTwitPublishedLessThanHourAgo(passedMinutesFromPublishedTwit) ->{
            "${passedMinutesFromPublishedTwit}m"
        }
        passedHoursFromPublishedTwit > 0 -> {
            "${passedHoursFromPublishedTwit}h"
        }
        else -> {
            "..."
        }
    }

}


/**
 *The method converts date from one format to another
 * For example - from "8.16.2020" to "Sunday, 16"
 */
private fun convertDate(convertedFormatDate: String, dateString: String): String {
    val requestFormat = SimpleDateFormat(REQUEST_FORMAT_DATE, Locale.ENGLISH)
    val result = requestFormat.parse(dateString)

    return SimpleDateFormat(convertedFormatDate, Locale.ENGLISH).format(result ?: "")
}

/**
 * The method takes a date or time format and returns the current date or time in string format
 * For example - the method takes "MM/yyyy HH:mm" and return current date and time "o5/2021 5:45
 */
private fun getCurrentDateOrTime(convertedFormatDate: String): String {
    val result  = SimpleDateFormat(convertedFormatDate, Locale.ENGLISH)

    return result.format(Date())
}

/**
 * The method checks if current Month or Year is greater than Month or Year when twit was published
 */
private fun isCurrentMonthOrYearGreater(publishedTwitDate: String): Boolean {

    val publishedTwitMonthAndYear = publishedTwitDate.split("/")
    val publishedTwitYear = publishedTwitMonthAndYear[1].toInt()
    val publishedTwitMonth = publishedTwitMonthAndYear[0].toInt()

    val currentMonth = getCurrentDateOrTime(DATE_FORMAT_MONTH).toInt()
    val currentYear = getCurrentDateOrTime(DATE_FORMAT_YEAR).toInt()

    return currentMonth > publishedTwitMonth || currentYear > publishedTwitYear
}

/**
 * The method returns the number of minutes passed since the twit was posted
 */
private fun getPassedMinutesFromPublishedTwit(dateString: String): Int {
    val sdf = SimpleDateFormat(REQUEST_FORMAT_DATE, Locale.ENGLISH)
    val strDate: Date? = sdf.parse(dateString)
    val oldMillis = strDate?.time

    val currentMillis = System.currentTimeMillis()
    var passedMinutes = 0

    oldMillis?.let {
        passedMinutes = ((currentMillis - it) /  MILLISECONDS_IN_MINUTE).toInt()
    }

    return passedMinutes
}

/**
 *The method checks if less than an hour has passed since the tweet was published
 */
private fun isTwitPublishedLessThanHourAgo(passedMinutes: Int): Boolean {
    return passedMinutes < MINUTES_IN_HOUR

}

/**
 * The method checks the current day of the month is greater than the day of the month when the twit was posted
 * if greater than 6 days then displays the name and the day of the month in UI when twit was published: "May 8"
 * if less, then display the number of passed days since the twit was published: "3d"
 */
private fun isCurrentDayOfMonthGreaterThenPublishedTwitDay(passedDays: Int): Boolean {

    return when {
        passedDays > 6 -> false
        passedDays in 1..6 -> true
        else -> false
    }

}
