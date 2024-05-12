package com.example.core.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CityEntity(
    @PrimaryKey
    val id: String,
    val province_id: String,
    val name: String
)
