package com.example.databasexmlcourse.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.databasexmlcourse.domain.models.DishCategory
import com.example.databasexmlcourse.domain.models.UserType
import java.util.UUID

@Entity(tableName = "dish_categories")
data class DishCategoriesEntity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val name: String
)

fun DishCategoriesEntity.asExternalModel() = DishCategory(
    id = id,
    name = name
)
fun DishCategory.asEntity() = DishCategoriesEntity(
    name = name
)
