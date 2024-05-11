package com.example.core.util

import androidx.room.TypeConverter
import com.example.core.model.response.product.ProductRatingResponse
import com.google.gson.Gson
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

    @TypeConverter
    fun ratingToString(item: ProductRatingResponse) = Gson().toJson(item)

    @TypeConverter
    fun stringToRating(json: String) = Gson().fromJson(json, ProductRatingResponse::class.java)
}