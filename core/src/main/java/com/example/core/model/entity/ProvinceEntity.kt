package com.example.core.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ProvinceEntity(
    @PrimaryKey
    val id: String,
    val name: String
)