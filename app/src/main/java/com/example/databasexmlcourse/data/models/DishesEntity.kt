package com.example.databasexmlcourse.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.databasexmlcourse.domain.models.DishItem
import com.example.databasexmlcourse.domain.models.User
import java.util.UUID

@Entity(
    tableName = "dishes",
    foreignKeys = [ForeignKey(
        entity = DishCategoriesEntity::class,
        parentColumns = ["id"],
        childColumns = ["dishCategoryId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["dishCategoryId"])]
)
data class DishesEntity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val name: String,
    val price: Int,
    @ColumnInfo(name = "dishCategoryId") val dishCategoryId: String,
    val count: Int
)

fun DishesEntity.asExternalModel() = DishItem(
    id = id,
    name = name,
    price = price,
    dishCategoryId = dishCategoryId,
    count = count
)
fun DishItem.asEntity() = DishesEntity(
    id = id.ifBlank { UUID.randomUUID().toString() },
    name = name,
    price = price,
    dishCategoryId = dishCategoryId,
    count = count
)