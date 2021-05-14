package org.android.learning.sunflower.data

import androidx.room.TypeConverter
import java.util.*

class CalendarConverter {

    @TypeConverter
    fun datestampToCalendar(datestamp: Long): Calendar =
        Calendar.getInstance().apply { timeInMillis = datestamp }

    @TypeConverter
    fun calendarToDatestamp(calendar: Calendar): Long = calendar.timeInMillis

}