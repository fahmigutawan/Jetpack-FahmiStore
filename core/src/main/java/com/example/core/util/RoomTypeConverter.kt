package com.example.core.util

import androidx.room.TypeConverter
import java.util.Date

class RoomTypeConverter {
    @TypeConverter
    fun dateObjectToLong(date: Date) = date.time

    @TypeConverter
    fun dateLongToObject(date: Long) = Date(date)

    @TypeConverter
    fun listStringToString(list: List<String>) = list.joinToString(",")

    @TypeConverter
    fun stringToListString(string: String) = string.split(",")
}