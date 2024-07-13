package com.example.databasexmlcourse.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "dishes")
data class DishesEntity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val name: String,
    val price: Int,
    val dishCategoryId: DishCategoriesEntity
)
