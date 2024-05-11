package com.example.core.util

import androidx.room.TypeConverter
import com.example.core.model.response.kategori.SingleCategoryResponse
import com.google.gson.Gson
import java.util.Date

class RoomTypeConverter {
    @TypeConverter
    fun dateObjectToLong(date: Date) = date.time

    @TypeConverter
    fun dateLongToObject(date: Long) = Date(date)

    @TypeConverter
    fun categoryResponseToString(obj: SingleCategoryResponse) = Gson().toJson(obj)

    @TypeConverter
    fun categoryStringToResponse(json: String) =
        Gson().fromJson(json, SingleCategoryResponse::class.java)

    @TypeConverter
    fun listStringToString(list: List<String>) = list.joinToString(",")

    @TypeConverter
    fun stringToListString(string: String) = string.split(",")
}