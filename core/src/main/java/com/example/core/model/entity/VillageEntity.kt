package com.example.core.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class VillageEntity(
    @PrimaryKey
    val id: String,
    val district_id: String,
    val name: String
)
