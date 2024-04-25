package com.androidtest.presistence

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MainModelEntity(
    @PrimaryKey  val id: String,
    val description: String,
    val title: String,
    val imageSrc: String,
)
