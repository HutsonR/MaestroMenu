package com.example.databasexmlcourse.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "dish_categories")
data class DishCategoriesEntity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val name: String
)